@Grab(group='org.apache.solr', module='solr-solrj', version='3.3.0')
@Grab(group='org.slf4j', module='slf4j-jdk14', version='1.6.1')
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer
import org.apache.solr.common.SolrInputDocument

String url = "http://localhost:8983/solr"
def server = new CommonsHttpSolrServer( url );

def doc = new SolrInputDocument()
def nextId = java.util.UUID.randomUUID()
doc.addField "id", nextId
doc.addField "stock_s", "XYZ AB"
doc.addField "desc_txt", "Tech Company"
doc.addField "date_tdt", new Date()

server.add doc
server.commit()

println 'done'




