@Grab('org.apache.solr:solr-solrj:4.6.1')
 
import org.apache.solr.client.solrj.impl.CloudSolrServer
import org.apache.solr.client.solrj.SolrQuery
 
def props = new Properties()
new File( 'property_file.properties').withInputStream { 
    stream -> props.load(stream) }
 
def server = new CloudSolrServer(props['zk'])
def query = new SolrQuery()
query.setQuery("*:*")
query.setRows(3) 
def ret =  server.query(query)
println ret 
