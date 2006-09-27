<map version="0.8.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1158060659353" ID="Freemind_Link_507004273" MODIFIED="1158571537185" TEXT="jee">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1158060693295" ID="_" MODIFIED="1158560297701" POSITION="right" TEXT="ejb">
<node COLOR="#000000" CREATED="1158060719076" ID="Freemind_Link_687488324" MODIFIED="1158750788403" TEXT="session beans " VSHIFT="-28">
<node CREATED="1158560342075" ID="Freemind_Link_938084924" MODIFIED="1158560342075" TEXT=""/>
<node CREATED="1158560344476" ID="Freemind_Link_528229697" MODIFIED="1158560344476" TEXT=""/>
</node>
<node CREATED="1158236901935" HGAP="83" ID="Freemind_Link_969681646" MODIFIED="1158750752015" TEXT="entity beans" VSHIFT="-10">
<node CREATED="1158560346691" ID="Freemind_Link_377802501" MODIFIED="1158560351335" TEXT="persistence unit">
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
</node>
<node CREATED="1158562228759" ID="Freemind_Link_1574630246" MODIFIED="1158562270316" TEXT="logical groupiing of entities, mapping meta data and database connection info"/>
</node>
<node CREATED="1158560587915" ID="Freemind_Link_1524523943" MODIFIED="1158560593554" TEXT="persistence context">
<node CREATED="1158560678662" ID="Freemind_Link_952894869" MODIFIED="1158755767338" TEXT="a set of managed entities - managed by an EntityManager">
<font NAME="SansSerif" SIZE="12"/>
<node CREATED="1158560641023" ID="Freemind_Link_138861047" MODIFIED="1158755767336" TEXT="EntityManger (aka SessionFactory in Hibernate)">
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
<node CREATED="1158561293810" ID="Freemind_Link_1743581292" MODIFIED="1158561538541" TEXT="states">
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
<node CREATED="1158570583085" ID="Freemind_Link_1277154415" MODIFIED="1158570588153" TEXT="life cycle callbacks">
<node COLOR="#000000" CREATED="1158570589235" ID="Freemind_Link_1781314400" MODIFIED="1158570864680" TEXT="annotation in our entity class (e.g. for PrePersist)&#xa;">
<edge COLOR="#000000"/>
<node COLOR="#0033ff" CREATED="1158570771682" ID="Freemind_Link_695952533" MODIFIED="1158570789483" TEXT="@PrePersist void prePersist() &#xa;{// do something   }"/>
</node>
<node CREATED="1158570730579" HGAP="29" ID="Freemind_Link_1812518082" MODIFIED="1158570899578" TEXT="using a listener for callbacks on our entity" VSHIFT="6">
<node CREATED="1158571176166" ID="Freemind_Link_421365245" MODIFIED="1158571203641" TEXT="declare a listener class and annotate the &#xa;entity class with our listener class"/>
</node>
</node>
<node CREATED="1158571247636" ID="Freemind_Link_658945345" MODIFIED="1158571261528" TEXT="database synch modes - flush modes">
<node CREATED="1158571262785" ID="Freemind_Link_224739730" MODIFIED="1158571289868" TEXT="COMMIT - only synchs at commit time"/>
<node CREATED="1158571291072" ID="Freemind_Link_331322737" MODIFIED="1158571298866" TEXT="AUTO - synchs before aquery"/>
</node>
<node CREATED="1158571767612" ID="Freemind_Link_1511415625" MODIFIED="1158571774719" TEXT="concurrent access - locking">
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
<node CREATED="1158754982989" ID="Freemind_Link_1431976083" MODIFIED="1158754985657" TEXT="mappings">
<node CREATED="1158754986638" ID="Freemind_Link_1845521885" MODIFIED="1158755063047" TEXT="@Column and @Table mappings can be omiited for an entity -&gt;&#xa;defaults to the name of the field and the class name"/>
</node>
</node>
<node CREATED="1158060725056" ID="Freemind_Link_1728307174" MODIFIED="1158060731807" TEXT="message driven beans"/>
</node>
<node CREATED="1158060743238" ID="Freemind_Link_633503477" MODIFIED="1158060745619" POSITION="left" TEXT="jndi"/>
<node CREATED="1158060753351" ID="Freemind_Link_774718115" MODIFIED="1158060755895" POSITION="left" TEXT="jms"/>
<node CREATED="1159213008211" ID="Freemind_Link_596161336" MODIFIED="1159213106758" POSITION="right" TEXT="annotations or deployment descriptor">
<node CREATED="1159213110242" ID="Freemind_Link_249032267" MODIFIED="1159213261465" TEXT="ejb-jar.xml -&gt; overrides the annotations - these annotations can not be overriden:&#xa;@Stateless, @Stateful, @MessageDriven, @Service, @Consumer)">
<node CREATED="1159213284006" ID="Freemind_Link_211696926" MODIFIED="1159213322677" TEXT="ejb-jar.xml does not need to specify all settings - &#xa;just the ones that overrides the annotations"/>
<node CREATED="1159213376169" FOLDED="true" ID="Freemind_Link_378437107" MODIFIED="1159213379668" TEXT="example">
<node CREATED="1159213380680" ID="Freemind_Link_20946554" MODIFIED="1159213390694" TEXT="&lt;ejb-jar&gt; &#xa;   &lt;description&gt;jBoss test application &lt;/description&gt;&#xa;   &lt;display-name&gt;Test&lt;/display-name&gt;&#xa;   &lt;enterprise-beans&gt;&#xa;      &lt;session&gt;&#xa;         &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;         &lt;remote&gt;org.jboss.ejb3.test.bank.Teller&lt;/remote&gt;&#xa;         &lt;ejb-class&gt;org.jboss.ejb3.test.bank.TellerBean&lt;/ejb-class&gt;&#xa;         &lt;session-type&gt;Stateless&lt;/session-type&gt;&#xa;         &lt;transaction-type&gt;Container&lt;/transaction-type&gt;&#xa;         &lt;ejb-ref&gt;&#xa;            &lt;ejb-ref-name&gt;ejb/Bank&lt;/ejb-ref-name&gt;&#xa;            &lt;ejb-ref-type&gt;Session&lt;/ejb-ref-type&gt;&#xa;            &lt;remote&gt;org.jboss.ejb3.test.bank.Bank&lt;/remote&gt;&#xa;            &lt;ejb-link&gt;Bank&lt;/ejb-link&gt;&#xa;            &lt;injection-target&gt;bank&lt;/injection-target&gt;&#xa;         &lt;/ejb-ref&gt;&#xa;         &lt;resource-ref&gt;&#xa;             &lt;res-ref-name&gt;java:/TransactionManager&lt;/res-ref-name&gt;&#xa;             &lt;res-type&gt;javax.transaction.TransactionManager&lt;/res-type&gt;&#xa;             &lt;res-auth&gt;Container&lt;/res-auth&gt;&#xa;             &lt;res-sharing-scope&gt;Shareable&lt;/res-sharing-scope&gt;&#xa;             &lt;injection-target&gt;setTransactionManager&lt;/injection-target&gt;&#xa;         &lt;/resource-ref&gt;&#xa;         &lt;resource-ref&gt;&#xa;             &lt;res-ref-name&gt;&lt;/res-ref-name&gt;&#xa;             &lt;res-type&gt;javax.ejb.TimerService&lt;/res-type&gt;&#xa;             &lt;res-auth&gt;Container&lt;/res-auth&gt;&#xa;             &lt;res-sharing-scope&gt;Shareable&lt;/res-sharing-scope&gt;&#xa;             &lt;injection-target&gt;ts&lt;/injection-target&gt;&#xa;         &lt;/resource-ref&gt;&#xa;         &lt;security-identity&gt;&#xa;            &lt;run-as&gt;&#xa;               &lt;role-name&gt;bankTeller&lt;/role-name&gt;&#xa;            &lt;/run-as&gt;&#xa;         &lt;/security-identity&gt;&#xa;      &lt;/session&gt;&#xa;      &lt;session&gt;&#xa;         &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;         &lt;remote&gt;org.jboss.ejb3.test.bank.Bank&lt;/remote&gt;&#xa;         &lt;ejb-class&gt;org.jboss.ejb3.test.bank.BankBean&lt;/ejb-class&gt;&#xa;         &lt;session-type&gt;Stateful&lt;/session-type&gt;&#xa;         &lt;transaction-type&gt;Container&lt;/transaction-type&gt;&#xa;         &lt;env-entry&gt;&#xa;            &lt;env-entry-name&gt;id&lt;/env-entry-name&gt;&#xa;            &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;&#xa;            &lt;env-entry-value&gt;5678&lt;/env-entry-value&gt;&#xa;         &lt;/env-entry&gt;&#xa;         &lt;resource-ref&gt;&#xa;            &lt;res-ref-name&gt;java:DefaultDS&lt;/res-ref-name&gt;&#xa;            &lt;res-type&gt;javax.sql.DataSource&lt;/res-type&gt;&#xa;            &lt;res-auth&gt;Container&lt;/res-auth&gt;&#xa;            &lt;res-sharing-scope&gt;Shareable&lt;/res-sharing-scope&gt;&#xa;            &lt;injection-target&gt;customerDb&lt;/injection-target&gt;&#xa;         &lt;/resource-ref&gt;&#xa;         &lt;interceptor&gt;org.jboss.ejb3.test.bank.FirstInterceptor&lt;/interceptor&gt;&#xa;         &lt;interceptor&gt;org.jboss.ejb3.test.bank.SecondInterceptor&lt;/interceptor&gt; &#xa;         &lt;callback-listener&gt;org.jboss.ejb3.test.bank.ExternalCallbackListener&lt;/callback-listener&gt;&#xa;      &lt;/session&gt;&#xa;   &lt;/enterprise-beans&gt;&#xa;   &lt;assembly-descriptor&gt;&#xa;      &lt;callback&gt;&#xa;         &lt;annotation&gt;PostConstruct&lt;/annotation&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;postConstruct&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/callback&gt; &#xa;      &lt;remove-list&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;remove&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/remove-list&gt; &#xa;      &lt;init-list&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;init&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/init-list&gt; &#xa;      &lt;security-role&gt;&#xa;         &lt;role-name&gt;bankCustomer&lt;/role-name&gt;&#xa;      &lt;/security-role&gt;&#xa;      &lt;security-role&gt;&#xa;         &lt;role-name&gt;bankTeller&lt;/role-name&gt;&#xa;      &lt;/security-role&gt;&#xa;      &lt;method-permission&gt;&#xa;         &lt;role-name&gt;bankCustomer&lt;/role-name&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;greetChecked&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/method-permission&gt;&#xa;      &lt;method-permission&gt;&#xa;         &lt;unchecked/&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;greetUnchecked&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/method-permission&gt;&#xa;      &lt;method-permission&gt;&#xa;         &lt;role-name&gt;bankTeller&lt;/role-name&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;getCustomerId&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;storeCustomerId&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/method-permission&gt;&#xa;      &lt;container-transaction&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;greetWithNotSupportedTransaction&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;         &lt;trans-attribute&gt;NotSupported&lt;/trans-attribute&gt;&#xa;      &lt;/container-transaction&gt;&#xa;      &lt;container-transaction&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;greetWithRequiredTransaction&lt;/method-name&gt;&#xa;            &lt;method-params&gt;&#xa;               &lt;method-param&gt;java.lang.String&lt;/method-param&gt;&#xa;            &lt;/method-params&gt;&#xa;         &lt;/method&gt;&#xa;         &lt;trans-attribute&gt;Required&lt;/trans-attribute&gt;&#xa;      &lt;/container-transaction&gt;&#xa;      &lt;container-transaction&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;*&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;         &lt;trans-attribute&gt;Required&lt;/trans-attribute&gt;&#xa;      &lt;/container-transaction&gt;&#xa;      &lt;exclude-list&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;excludedMethod&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/exclude-list&gt;&#xa;   &lt;/assembly-descriptor&gt;&#xa;&lt;/ejb-jar&gt;"/>
</node>
</node>
</node>
</node>
</map>
