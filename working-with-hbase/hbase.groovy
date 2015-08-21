@Grapes([
  @GrabResolver(name='cloudera.', root='https://repository.cloudera.com/artifactory/cloudera-repos/'),
  @Grab('org.apache.hbase:hbase:0.94.6-cdh4.5.0'),
  @Grab('org.apache.hadoop:hadoop-common:2.0.0-cdh4.5.0')
])
 
import org.apache.hadoop.hbase.client.*
import org.apache.hadoop.hbase.*
import org.apache.hadoop.conf.*
import org.apache.hadoop.hbase.util.*
import org.apache.hadoop.hbase.filter.*
 
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp
 
def props = new Properties()
new File( "myfile.properties").withInputStream { stream -> props.load(stream) }
 
config = new Configuration(["hbase.zookeeper.quorum": props['zk_hosts']])
HBaseConfiguration.create(config)
 
HBaseAdmin.checkHBaseAvailable(config)
 
table = new HTable(config, "my_table")
 
def getAllRecordsForTable(String table_name) {
 
    def a_table = new HTable(config, table_name)
    def scan = new Scan()
    scan.addColumn("colunmn_family"; as byte[], "column_qualifier" as byte[])
    for (result in a_table.getScanner(scan)) {
        for (KeyValue keyValue : result.list()) {
            println "\n\nQualifier : " + keyValue.getKeyString() + " : \nValue : " + Bytes.toString(keyValue.getValue())[0..150]
    }       }
    a_table.close()
 
}
 
getAllRecordsForTable("table_name")

def getById(String id) {
 
    def a_table = new HTable(config, "table_name")
    def get = new Get(id as byte[])
    def r = table.get(get)
    println new String(r.getRow())
}

def getHBaseRecord(String id) {
    def r = table.get(new Get(id as byte[]))
    return new Expando(
        provider: getHValue("family", "qualifier1", r),
        source_name: getHValue("family", "qualifier2", r),
        id: new String(r.getRow())
    )
}
 
def getHValue(String family, String qualifier, Result r) {
    def temp_val = r.getValue(family as byte[], qualifier as byte[])
    return (temp_val) ? new String(temp_val) : null
}

def findByName(String name) {
 
    def return_list = []
    def scan = new Scan()
    def filter = new SingleColumnValueFilter(
        "family" as byte[],
        "qualifier" as byte[],
        CompareOp.EQUAL,
        name as byte[]
    );
    scan.setFilter(filter);
 
    def count = 0
    for (e in table.getScanner(scan)) {
        def next = new String(e.getRow())
        if (next) {
            count++
            if (count % 25 == 0) println count
            def ob = getHBaseRecord(next)
            return_list << ob
        }
    }
    return return_list
}


def deleteByName(String name) {
    def scan = new Scan()
    def filter = new SingleColumnValueFilter(
        "family" as byte[],
        "qualifier" as byte[],
        CompareOp.EQUAL,
        name as byte[]
    );
    scan.setFilter(filter);
 
    def count = 0
    for (e in table.getScanner(scan)) {
        table.delete(new Delete(e.getRow()))
        count++
        if (count % 25 == 0) {
            println "$count deleted from $source_name"
        }
    }
    println "$count deleted from $source_name"
}

