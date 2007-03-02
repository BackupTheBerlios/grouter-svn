<map version="0.8.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1161067318453" ID="Freemind_Link_1334225130" MODIFIED="1161067341693" TEXT="entity beans">
<node CREATED="1158570583085" ID="Freemind_Link_1277154415" MODIFIED="1158570588153" POSITION="left" TEXT="life cycle callbacks">
<node COLOR="#000000" CREATED="1158570589235" ID="Freemind_Link_1781314400" MODIFIED="1158570864680" TEXT="annotation in our entity class (e.g. for PrePersist)&#xa;">
<edge COLOR="#000000"/>
<node COLOR="#0033ff" CREATED="1158570771682" ID="Freemind_Link_695952533" MODIFIED="1158570789483" TEXT="@PrePersist void prePersist() &#xa;{// do something   }"/>
</node>
<node CREATED="1158570730579" HGAP="29" ID="Freemind_Link_1812518082" MODIFIED="1158570899578" TEXT="using a listener for callbacks on our entity" VSHIFT="6">
<node CREATED="1158571176166" ID="Freemind_Link_421365245" MODIFIED="1158571203641" TEXT="declare a listener class and annotate the &#xa;entity class with our listener class"/>
</node>
</node>
<node CREATED="1158571767612" ID="Freemind_Link_1511415625" MODIFIED="1158571774719" POSITION="left" TEXT="concurrent access - locking">
<node CREATED="1158571775724" ID="Freemind_Link_373564263" MODIFIED="1158571780182" TEXT="optimistic locking">
<node CREATED="1158571780599" ID="Freemind_Link_1290340028" MODIFIED="1158571786093" TEXT="compare cvs and svn"/>
<node CREATED="1158571788412" ID="Freemind_Link_1480253040" MODIFIED="1158571826906" TEXT="write confilicts are detected at write time"/>
<node CREATED="1158571848724" ID="Freemind_Link_66232236" MODIFIED="1158571860530" TEXT="scalable"/>
</node>
<node CREATED="1158571892248" ID="Freemind_Link_1523605648" MODIFIED="1158571894367" TEXT="levels">
<node CREATED="1158571894722" ID="Freemind_Link_560285581" MODIFIED="1158571897929" TEXT="READ COMMITED"/>
<node CREATED="1158571899522" ID="Freemind_Link_1266547287" MODIFIED="1158571902779" TEXT="SERIALIZABLE"/>
</node>
</node>
<node CREATED="1158561293810" ID="Freemind_Link_1743581292" MODIFIED="1158561538541" POSITION="right" TEXT="states">
<node CREATED="1158569670957" HGAP="21" ID="Freemind_Link_1197680942" MODIFIED="1158755554917" TEXT="new " VSHIFT="-21">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1158570136156" ID="Freemind_Link_1233133733" MODIFIED="1158570138125" TEXT=" instance created in memory but synch with database is not done"/>
</node>
<node CREATED="1158561187837" ID="Freemind_Link_1880375447" MODIFIED="1158569724903" TEXT="managed state">
<edge STYLE="sharp_linear"/>
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1158755540964" ID="Freemind_Link_1237357417" MODIFIED="1158755546571" TEXT="i.e. attached"/>
<node CREATED="1158561193204" ID="Freemind_Link_1017265211" MODIFIED="1158561211913" TEXT="when under a transaction governed by the EntityManager"/>
<node CREATED="1158570166568" ID="Freemind_Link_1828339529" MODIFIED="1158570181224" TEXT="instance is persisted in database and under a persistence context"/>
<node CREATED="1158570213805" ID="Freemind_Link_1116253240" MODIFIED="1158570220611" TEXT="after a persiste method was called"/>
</node>
<node CREATED="1158561306857" ID="Freemind_Link_151483010" MODIFIED="1158569725502" TEXT="detached state">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1158561318619" ID="Freemind_Link_1242262984" MODIFIED="1158561333879" TEXT="entity will not be persisted when changed"/>
<node CREATED="1158570239178" ID="Freemind_Link_954657063" MODIFIED="1158570257960" TEXT="has identity in database but synch is not done"/>
<node CREATED="1158756223089" ID="Freemind_Link_665627801" MODIFIED="1158756241882" TEXT="if entity is serialazable it can be sent over the network"/>
<node CREATED="1158756274138" ID="Freemind_Link_705536313" MODIFIED="1158756291694" TEXT="no more Value Object Pattern (DTOs)"/>
</node>
<node CREATED="1158569688259" ID="Freemind_Link_362469855" MODIFIED="1158569726152" TEXT="removed">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1158570269791" ID="Freemind_Link_1652608996" MODIFIED="1158570274122" TEXT="scheduled for removal"/>
</node>
</node>
<node CREATED="1158754982989" ID="Freemind_Link_1431976083" MODIFIED="1160116639205" POSITION="right" TEXT="mapping">
<node CREATED="1158754986638" ID="Freemind_Link_1845521885" MODIFIED="1158755063047" TEXT="@Column and @Table mappings can be omiited for an entity -&gt;&#xa;defaults to the name of the field and the class name"/>
<node CREATED="1160116647337" ID="Freemind_Link_1195114954" MODIFIED="1160116655989" TEXT="one-to-many">
<node CREATED="1160116675627" ID="Freemind_Link_305437731" MODIFIED="1160116688798" TEXT="unidirectional">
<node CREATED="1160116969131" ID="Freemind_Link_983964036" MODIFIED="1160116974686" TEXT="using a join table">
<node CREATED="1160143616296" ID="Freemind_Link_1254812366" MODIFIED="1160143692518" TEXT="&lt;html&gt;&lt;img src=&quot;messagesender_onetomany_withjointable.png&quot;&gt;" VSHIFT="-35"/>
<node CREATED="1160141648017" ID="Freemind_Link_467334230" MODIFIED="1160143783026" TEXT="In the Message class:&#xa;    // Declaring this on the attribute creates tables (create-drop for ddl generation)&#xa;    // which are incorrect -&gt; it is eclared here on the getter instead.&#xa;    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})&#xa;    @JoinColumn(name = &quot;SENDER_ID&quot;, nullable = true)       &#xa;    public Sender getSender()&#xa;    {&#xa;        return sender;&#xa;    }&#xa;&#xa;In the Sender class:&#xa;    @OneToMany&#xa;    @JoinTable( name=&quot;SENDER_MESSAGE&quot;,&#xa;                    joinColumns = {@JoinColumn(name = &quot;SENDER_ID&quot;)},&#xa;                    inverseJoinColumns ={@JoinColumn( name = &quot;MESSAGE_ID&quot;)})&#xa;    private Set&lt;Message&gt; messages = new HashSet();&#xa;" VSHIFT="-8"/>
</node>
<node CREATED="1160116977155" ID="Freemind_Link_693553183" MODIFIED="1160126237886" TEXT="not using a join table" VSHIFT="104">
<node CREATED="1160116689307" ID="Freemind_Link_1067581159" MODIFIED="1160116903865" TEXT="&lt;html&gt;&lt;img src=&quot;Message_Sender_tables.png&quot;&gt;"/>
<node CREATED="1160117076548" ID="Freemind_Link_268854318" MODIFIED="1160143810585" TEXT="In Message class:&#xa;   @ManyToOne( cascade = {CascadeType.ALL, CascadeType.MERGE} )&#xa;   @JoinColumn(name = &quot;SENDER_ID&quot;, nullable = true)  //This i optional&#xa;   private Sender sender;&#xa;&#xa;In Sender class:&#xa;    // No duplicate elements and the ordering is not relevant for us -&gt; Set&#xa;    // The mapped by is not set since this is not a bidirectional relationship -&gt; the Message&#xa;    // is the root entity&#xa;    @OneToMany&#xa;    private Set&lt;Message&gt; messages = new HashSet();&#xa;"/>
</node>
</node>
</node>
<node CREATED="1161067580275" ID="_" MODIFIED="1161067587577" TEXT="inheritance">
<node CREATED="1161067601790" ID="Freemind_Link_635739137" MODIFIED="1161069810107" TEXT="Table per class hierarchy / JPA - SINGLE_TABLE">
<node CREATED="1161067661672" ID="Freemind_Link_1718532644" MODIFIED="1161069692969" TEXT="Easiest to use. &#xa;Best performing way to represent polymorphism&#x2014;both polymorphic and non-polymorphic quer- &#xa;ies perform very well&#x2014;and is even quite easy to implement by hand."/>
<node CREATED="1161067716314" ID="Freemind_Link_1330550864" MODIFIED="1161067717933" TEXT="All the classes in the hierarchy are stored in a single table"/>
<node CREATED="1161068412895" ID="Freemind_Link_860938927" MODIFIED="1161068537445" TEXT="The only obvious limitation is that your subclasses can&#x2018;t have columns declared as NOT NULL. Subclasses can&apos;t have non-null attributes because inserting the superclass, which doesn&#x2018;t even have the non-null attribute, will cause a null column violation when it&#x2018;s inserted into the database. &#xa;">
<node CREATED="1161069175234" ID="Freemind_Link_1927907651" MODIFIED="1161069183445" TEXT="One solution would be to use database-lev&#xa;el checking constraints. Depending on the DISCRIMINATOR value, you can define a set of rules to implement, as shown in Listing 12. Of course, your database engine has to support this feature. Furthermore, since those constraints must be expressed in a single valid expression for all concrete classes and at the same time, it can become difficult to maintain as the hierarchy grows."/>
<node CREATED="1161069904200" ID="Freemind_Link_1221835601" MODIFIED="1161069905843" TEXT="The disadvantages of the table per class hierarchy strategy might be too serious for your  design&#x2014;after all, denormalized schemas can become a major burden in the long run. Your DBA  might not like it at all."/>
</node>
<node CREATED="1161068498426" ID="Freemind_Link_400448645" MODIFIED="1161068507165" TEXT="&lt;html&gt;&lt;img src=&quot;manning3.jpg&quot;&gt;"/>
<node CREATED="1161068566460" ID="Freemind_Link_1376616961" MODIFIED="1161068568935" TEXT="mapping">
<node CREATED="1161068597693" ID="Freemind_Link_1638231185" MODIFIED="1161068728881" TEXT="&lt;class name=&quot;Event&quot;table=&quot;events&quot;discriminator-value=&quot;EVENT&quot;&gt;         &#xa;  &lt;id name=&quot;id&quot;type=&quot;long&quot;&gt;&lt;generator class=&quot;native&quot;/&gt;&lt;/id&gt;         &#xa;  &lt;discriminator column=&quot;event_type&quot;type=&quot;string&quot;length=&quot;15&quot;/&gt;         &#xa;  ...         &#xa;  &lt;subclass name=&quot;ConferenceEvent&quot;discriminator-value=&quot;CONF_EVENT&quot;&gt;             &#xa;     &lt;property name=&quot;numberOfSeats&quot;column=&quot;num_seats&quot;/&gt;&#xa;     ...       &#xa;  &lt;/subclass&gt;       &#xa;  &lt;subclass name=&quot;NetworkingEvent&quot;discriminator-value=&quot;NET_EVENT&quot;&gt;          &#xa;     &lt;property name=&quot;foodProvided&quot;column=&quot;food_provided&quot;/&gt;          &#xa;     ...       &#xa;  &lt;/subclass&gt;     &#xa;&lt;/class&gt;&#xa;"/>
</node>
<node CREATED="1161068751154" ID="Freemind_Link_157225597" MODIFIED="1161068766269" TEXT="The discriminator is only a column in the relational table-you &#xa;don&apos;t need to define it as a property in your Java object."/>
<node CREATED="1161068843669" ID="Freemind_Link_1002266559" MODIFIED="1161068869905" TEXT="Swiss cheese table - because it is full of holes."/>
<node CREATED="1161069047382" ID="Freemind_Link_85820723" MODIFIED="1161069049482" TEXT="sql">
<node CREATED="1161069050446" ID="Freemind_Link_1751376227" MODIFIED="1161069087240" TEXT="Effective SQL statements when accessing the database. When we query a concrete class Hibernate filters on the discriminator value automatically -- a good thing, because it means that Hibernate reads only the appropriate columns for the specified class.&#xa;"/>
</node>
</node>
<node CREATED="1161067614095" ID="Freemind_Link_710169159" MODIFIED="1161067634858" TEXT="Table per subclass">
<edge WIDTH="thin"/>
<node CREATED="1161069256542" ID="Freemind_Link_1066489791" MODIFIED="1161069257938" TEXT="Instead of putting all the classes into a single table, you can choose to put each subclass into its own table. This approach eliminates the discriminator column and introduces a one-to-one mapping from the sub-class tables to the superclass table."/>
<node CREATED="1161069295199" ID="Freemind_Link_1232975683" MODIFIED="1161069302766" TEXT="&lt;html&gt;&lt;img src=&quot;manning4.jpg&quot;&gt;"/>
<node CREATED="1161069399451" ID="Freemind_Link_983600858" MODIFIED="1161069402374" TEXT="mapping">
<node CREATED="1161069432124" ID="Freemind_Link_1044356931" MODIFIED="1161069457522" TEXT="&lt;class name=&quot;Event&quot;table=&quot;events&quot;&gt;&#xa;  &lt;id name=&quot;event_id&quot;type=&quot;long&quot;&gt;&lt;generator class=&quot;native&quot;/&gt;&lt;/id&gt;&#xa;  &lt;joined-subclass name=&quot;ConferenceEvent&quot;table=&quot;conf_events&quot;&gt;&#xa;          &lt;key column=&quot;event_id&quot;/&gt;&#xa;          ...&#xa;  &lt;/joined-subclass&gt;&#xa;  &lt;joined-subclass name=&quot;NetworkingEvent&quot;table=&quot;net_events&quot;&gt;&#xa;          &lt;key column=&quot;event_id&quot;/&gt;&#xa;          ...&#xa;  &lt;/joined-subclass&gt;&#xa;&lt;/class&gt;"/>
</node>
</node>
<node CREATED="1161067620766" ID="Freemind_Link_1464489192" MODIFIED="1161067624674" TEXT="Table per concrete class"/>
</node>
</node>
<node CREATED="1158560587915" ID="Freemind_Link_1524523943" MODIFIED="1158560593554" POSITION="left" TEXT="persistence context">
<node CREATED="1158560678662" ID="Freemind_Link_952894869" MODIFIED="1158755767338" TEXT="a set of managed entities - managed by an EntityManager">
<font NAME="SansSerif" SIZE="12"/>
<node CREATED="1158560641023" FOLDED="true" ID="Freemind_Link_138861047" MODIFIED="1158755767336" TEXT="EntityManger (aka SessionFactory in Hibernate)">
<node CREATED="1158755451180" ID="Freemind_Link_1098670699" MODIFIED="1158755456748" TEXT="caching"/>
<node CREATED="1158755465428" ID="Freemind_Link_235877260" MODIFIED="1158755468860" TEXT="jta support"/>
<node CREATED="1158569529381" ID="Freemind_Link_1239316322" MODIFIED="1158569569936" TEXT="container managed - injected"/>
<node CREATED="1158569534780" ID="Freemind_Link_1256359130" MODIFIED="1158569577211" TEXT="application managed - through api"/>
<node CREATED="1158570470337" ID="Freemind_Link_729879325" MODIFIED="1158570472944" TEXT="interface">
<node CREATED="1158570473474" ID="Freemind_Link_676905356" MODIFIED="1158570476006" TEXT="persist"/>
<node CREATED="1158570477462" ID="Freemind_Link_898572060" MODIFIED="1158570479169" TEXT="merge"/>
<node CREATED="1158570480099" ID="Freemind_Link_1103213353" MODIFIED="1158570481556" TEXT="remove">
<node CREATED="1158570524236" ID="Freemind_Link_1139468455" MODIFIED="1158570540242" TEXT="raises IllegalArgumentException if done on a detached entity"/>
</node>
<node CREATED="1158571319209" ID="Freemind_Link_1307287733" MODIFIED="1158571321527" TEXT="flush">
<node CREATED="1158571322959" ID="Freemind_Link_1320232712" MODIFIED="1158571330715" TEXT="synchs the state with database"/>
</node>
<node CREATED="1158571344346" ID="Freemind_Link_360759723" MODIFIED="1158571360139" TEXT="setFlushMode"/>
<node CREATED="1158571602828" ID="Freemind_Link_627791331" MODIFIED="1158571605098" TEXT="refresh"/>
</node>
<node CREATED="1159552282974" ID="Freemind_Link_1556186706" MODIFIED="1159552289064" TEXT="injected">
<node CREATED="1159552289680" ID="Freemind_Link_507927761" MODIFIED="1159552333107" TEXT="@PersistenceContext(unitName=&quot;ejb3trail&quot;)&#xa;EntityManager em;"/>
</node>
</node>
</node>
<node CREATED="1158561416376" ID="Freemind_Link_1662216155" MODIFIED="1158561508008" TEXT="context types">
<node CREATED="1158561422144" ID="Freemind_Link_543007675" MODIFIED="1158561782755" TEXT="transaction scoped">
<font NAME="SansSerif" SIZE="12"/>
<icon BUILTIN="full-1"/>
<node CREATED="1158755837096" ID="Freemind_Link_932574051" MODIFIED="1158755843866" TEXT="only in JEE container"/>
<node CREATED="1158561683340" ID="Freemind_Link_1438729230" MODIFIED="1158755969750" TEXT="ends when enclosing JTA transaction ends"/>
</node>
<node CREATED="1158561439865" ID="Freemind_Link_1551334750" MODIFIED="1158561781060" TEXT="extended">
<font NAME="SansSerif" SIZE="12"/>
<icon BUILTIN="full-2"/>
<node COLOR="#0033ff" CREATED="1158561858645" ID="Freemind_Link_464651321" MODIFIED="1158760740117" TEXT="/** injected by the container */&#xa;@PersistenceContext(PersistenceContextType.EXTENDED,unitname=&quot;grouterDomain&quot;)&#xa;private EntityManager entityManager;" VSHIFT="-25">
<node CREATED="1158562006651" HGAP="21" ID="Freemind_Link_814450450" MODIFIED="1158760737365" TEXT="This means that we do not need to perform a lookup in &#xa;all business oeprations to retrieve the entity. The persistence &#xa;spans multiple transactions and are only removed upon&#xa;bean removal from pool." VSHIFT="-19"/>
</node>
<node CREATED="1158756160477" ID="Freemind_Link_1528558245" MODIFIED="1158756173709" TEXT="can be done either using">
<node CREATED="1158561553807" ID="Freemind_Link_1225312093" MODIFIED="1158760732901" TEXT="in jee container this id done with stateful session beans" VSHIFT="-32">
<node CREATED="1158561632582" ID="Freemind_Link_1210409495" MODIFIED="1158561655970" TEXT="ends when statefull sb is removed"/>
</node>
<node CREATED="1158756179223" ID="Freemind_Link_1689304876" MODIFIED="1158756198783" TEXT="programmatically with application code">
<node CREATED="1158760614678" HGAP="21" ID="Freemind_Link_630473657" MODIFIED="1158760728421" TEXT="EntityManagerFactory emf = Persistence.createEntityManagerFactory(&quot;grouterdomainmodel&quot;);&#xa;...&#xa;EntityManager em = emf.createEntityManager();&#xa;em.getTransaction().begin();&#xa;em.persist(message);&#xa;em.getTransaction().commit();&#xa;em.close();" VSHIFT="-22"/>
</node>
</node>
</node>
</node>
<node CREATED="1158570384588" ID="Freemind_Link_752042403" MODIFIED="1158570391370" TEXT="in code you declare it like">
<node COLOR="#0033ff" CREATED="1158560738330" ID="Freemind_Link_344970623" MODIFIED="1158561145559" TEXT="/** injected by the container */&#xa;@PersistenceContext&#xa;private EntityManager entityManager;&#xa;"/>
</node>
</node>
<node CREATED="1158560346691" ID="Freemind_Link_377802501" MODIFIED="1158560351335" POSITION="left" TEXT="persistence unit">
<node CREATED="1158560352419" ID="Freemind_Link_326771652" MODIFIED="1158750870862" TEXT="persistence.xml" VSHIFT="-16">
<node CREATED="1158750969540" ID="Freemind_Link_1257243051" MODIFIED="1158754234959" TEXT="one or more persistence units"/>
<node CREATED="1158560423144" ID="Freemind_Link_1755221186" MODIFIED="1158754276220" TEXT="no enitities declared within xml -&gt; &#xa;all entities are considered part of persistence unit">
<node CREATED="1158756542420" ID="Freemind_Link_1138429968" MODIFIED="1158756573205" TEXT="enitites marked with @Entity"/>
</node>
<node CREATED="1158562157445" ID="Freemind_Link_384705206" MODIFIED="1158569377495" TEXT="must be present with at least one child node with a name"/>
<node CREATED="1158569379021" ID="Freemind_Link_1374841928" MODIFIED="1158754454117" TEXT="can be put into an ear, war or jar - us">
<node CREATED="1158756363836" ID="Freemind_Link_199731351" MODIFIED="1158756363836" TEXT=""/>
</node>
<node CREATED="1158756367474" ID="Freemind_Link_640854317" MODIFIED="1158756370906" TEXT="pacakged using">
<node CREATED="1158756371374" ID="Freemind_Link_1503398787" MODIFIED="1158756373455" TEXT="jar">
<node CREATED="1158756487058" ID="Freemind_Link_1603934296" MODIFIED="1158756490552" TEXT="within the classpath"/>
</node>
<node CREATED="1158756374273" ID="Freemind_Link_1562649636" MODIFIED="1158756375630" TEXT="war">
<node CREATED="1158756397373" ID="Freemind_Link_1028002946" MODIFIED="1158756410242" TEXT="within the WEB-INF/lib folder as a jar"/>
</node>
<node CREATED="1158756376636" ID="Freemind_Link_1640682865" MODIFIED="1158756380880" TEXT="ear">
<node CREATED="1158756429435" ID="Freemind_Link_808444115" MODIFIED="1158756442554" TEXT="within a jar file under the ear/lib folder"/>
<node CREATED="1158756443934" ID="Freemind_Link_19418124" MODIFIED="1158756471667" TEXT="within a jar file under the root of the ear"/>
</node>
<node CREATED="1158760310636" ID="Freemind_Link_1805274286" MODIFIED="1158760313241" TEXT="par">
<node CREATED="1158760313819" ID="Freemind_Link_1216906869" MODIFIED="1158760332864" TEXT="jee container is repsonsible for scanning &quot;jar&quot;"/>
</node>
</node>
<node CREATED="1159552304906" ID="Freemind_Link_458612324" MODIFIED="1159552310157" TEXT="exampple">
<node CREATED="1159552311153" ID="Freemind_Link_1526143574" MODIFIED="1159552313573" TEXT="&lt;persistence-unit name=&quot;grouterDomain&quot; transaction-type=&quot;JTA&quot;&gt;         &lt;description&gt;Data source for Grouter&lt;/description&gt;         &lt;jta-data-source&gt;java:/DefaultDS&lt;/jta-data-source&gt;         &lt;!--jar-file&gt;../MyApp.jar&lt;/jar-file&gt;         &lt;class&gt;org.grouter.domain.Message&lt;/class&gt;         &lt;class&gt;org.acme.Person&lt;/class&gt;         &lt;class&gt;org.acme.Address&lt;/class--&gt;         &lt;properties&gt;             &lt;property name=&quot;hibernate.hbm2ddl.auto&quot; value=&quot;create&quot;/&gt;             &lt;property name=&quot;hibernate.dialect&quot; value=&quot;org.hibernate.dialect.HSQLDialect&quot;/&gt;             &lt;property name=&quot;hibernate.connection.driver_class&quot; value=&quot;org.hsqldb.jdbcDriver&quot;/&gt;     "/>
</node>
</node>
<node CREATED="1158562228759" ID="Freemind_Link_1574630246" MODIFIED="1158562270316" TEXT="logical groupiing of entities, mapping meta data and database connection info"/>
</node>
<node CREATED="1158571247636" ID="Freemind_Link_658945345" MODIFIED="1158571261528" POSITION="right" TEXT="database synch modes - flush modes">
<node CREATED="1158571262785" ID="Freemind_Link_224739730" MODIFIED="1158571289868" TEXT="COMMIT - only synchs at commit time"/>
<node CREATED="1158571291072" ID="Freemind_Link_331322737" MODIFIED="1158571298866" TEXT="AUTO - synchs before aquery"/>
</node>
</node>
</map>
