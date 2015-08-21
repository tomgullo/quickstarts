@Grab(group='org.apache.solr', module='solr-solrj', version='3.3.0')
@Grab(group='org.slf4j', module='slf4j-jdk14', version='1.6.1')
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer
import org.apache.solr.client.solrj.SolrQuery

String url = "http://localhost:8983/solr"
def server = new CommonsHttpSolrServer( url )

String inputParam = "*:*"
String facetParam = 'stock_s:"XYZ AB"'

def query = new SolrQuery().
    setQuery(inputParam).
    setHighlight(true).setParam("hl.fl", "*").
    setHighlightSimplePre("<b>").setHighlightSimplePost("</b>"). 
    setFacet(true).setFacetMinCount(1).
    //if stock_s in parameter add filter query else add facet field
    //addFilterQuery(facetParam).
    addFacetField("stock_s")
def response = server.query(query)
def docs = []
for (e in response.getResults()) {
    def expando = new Expando(id:e.id, stock:e.stock_s, desc:e.desc_txt, date:e.date_tdt)
    def hi = response.getHighlighting().get(e.id)
    if (hi) {
        for (e2 in hi) {
            if (e2.key == 'desc_txt') { expando.desc = hi.get("desc_txt") }
        }
    }
    docs << expando
}
def stocks  = response.getFacetField("stock_s")?.getValues()
println stocks

println docs




