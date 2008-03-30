<map version="0.8.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1158060659353" ID="Freemind_Link_507004273" MODIFIED="1158571537185" TEXT="jee">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1158060693295" ID="_" MODIFIED="1158560297701" POSITION="right" TEXT="ejb">
<node COLOR="#000000" CREATED="1158060719076" ID="Freemind_Link_687488324" MODIFIED="1158750788403" TEXT="session beans " VSHIFT="-28">
<node CREATED="1158560342075" ID="Freemind_Link_938084924" MODIFIED="1159218683125" TEXT="no extends java.rmi.Remote, just a Plain Old Java Interface&#xa;no throws RemoteException&#xa;the annotation @Remote&#xa;"/>
<node CREATED="1158560344476" ID="Freemind_Link_528229697" MODIFIED="1158560344476" TEXT=""/>
</node>
<node CREATED="1158060725056" HGAP="150" ID="Freemind_Link_1728307174" MODIFIED="1159450721487" TEXT="message driven beans" VSHIFT="30"/>
<node CREATED="1159443571154" HGAP="32" ID="Freemind_Link_844459102" MODIFIED="1159450576338" TEXT="ejb reference" VSHIFT="47">
<node CREATED="1159443576054" ID="Freemind_Link_497672413" MODIFIED="1159557904858" TEXT="In Jboss when deployed as ear:&#xa;@EJB (mappedName&quot;EARNAME/BEANCLASSNAME/remote&quot;) "/>
<node CREATED="1159443658040" ID="Freemind_Link_630691913" MODIFIED="1159558116639" TEXT="New method - lookup - in javax.ejb.EJBContext:&#xa;@Resource&#xa;private SessionContext sc;&#xa;public void setup()&#xa;{     &#xa;     //if deployed in ear  &quot;ear/ejb/local or remote&quot;&#xa;     FirstStatelessInterface fi=(FirstStatelessInterface)sc.lookup(&quot;domain/GRouterBean/local&quot;);  &#xa;     // if deployed in jar -&gt; skip the ear name (domain)   &#xa;}"/>
<node CREATED="1159448377958" ID="Freemind_Link_859070461" MODIFIED="1159448414528" TEXT="Using a setter method:&#xa;   private Calculator set;&#xa;   @EJB(beanName=&quot;org.jboss.tutorial.injection.bean.CalculatorBean&quot;)&#xa;   public void setCalculator(Calculator c)&#xa;   {&#xa;      set = c;&#xa;   }"/>
</node>
</node>
<node CREATED="1158060743238" ID="Freemind_Link_633503477" MODIFIED="1158060745619" POSITION="left" TEXT="jndi"/>
<node CREATED="1158060753351" ID="Freemind_Link_774718115" MODIFIED="1159214008102" POSITION="left" TEXT="jms" VSHIFT="86"/>
<node CREATED="1159213008211" ID="Freemind_Link_596161336" MODIFIED="1159213106758" POSITION="right" TEXT="annotations or deployment descriptor">
<node CREATED="1159213110242" ID="Freemind_Link_249032267" MODIFIED="1159213261465" TEXT="ejb-jar.xml -&gt; overrides the annotations - these annotations can not be overriden:&#xa;@Stateless, @Stateful, @MessageDriven, @Service, @Consumer)">
<node CREATED="1159213284006" ID="Freemind_Link_211696926" MODIFIED="1159213322677" TEXT="ejb-jar.xml does not need to specify all settings - &#xa;just the ones that overrides the annotations"/>
<node CREATED="1159213376169" FOLDED="true" ID="Freemind_Link_378437107" MODIFIED="1159213379668" TEXT="example">
<node CREATED="1159213380680" ID="Freemind_Link_20946554" MODIFIED="1159213390694" TEXT="&lt;ejb-jar&gt; &#xa;   &lt;description&gt;jBoss test application &lt;/description&gt;&#xa;   &lt;display-name&gt;Test&lt;/display-name&gt;&#xa;   &lt;enterprise-beans&gt;&#xa;      &lt;session&gt;&#xa;         &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;         &lt;remote&gt;org.jboss.ejb3.test.bank.Teller&lt;/remote&gt;&#xa;         &lt;ejb-class&gt;org.jboss.ejb3.test.bank.TellerBean&lt;/ejb-class&gt;&#xa;         &lt;session-type&gt;Stateless&lt;/session-type&gt;&#xa;         &lt;transaction-type&gt;Container&lt;/transaction-type&gt;&#xa;         &lt;ejb-ref&gt;&#xa;            &lt;ejb-ref-name&gt;ejb/Bank&lt;/ejb-ref-name&gt;&#xa;            &lt;ejb-ref-type&gt;Session&lt;/ejb-ref-type&gt;&#xa;            &lt;remote&gt;org.jboss.ejb3.test.bank.Bank&lt;/remote&gt;&#xa;            &lt;ejb-link&gt;Bank&lt;/ejb-link&gt;&#xa;            &lt;injection-target&gt;bank&lt;/injection-target&gt;&#xa;         &lt;/ejb-ref&gt;&#xa;         &lt;resource-ref&gt;&#xa;             &lt;res-ref-name&gt;java:/TransactionManager&lt;/res-ref-name&gt;&#xa;             &lt;res-type&gt;javax.transaction.TransactionManager&lt;/res-type&gt;&#xa;             &lt;res-auth&gt;Container&lt;/res-auth&gt;&#xa;             &lt;res-sharing-scope&gt;Shareable&lt;/res-sharing-scope&gt;&#xa;             &lt;injection-target&gt;setTransactionManager&lt;/injection-target&gt;&#xa;         &lt;/resource-ref&gt;&#xa;         &lt;resource-ref&gt;&#xa;             &lt;res-ref-name&gt;&lt;/res-ref-name&gt;&#xa;             &lt;res-type&gt;javax.ejb.TimerService&lt;/res-type&gt;&#xa;             &lt;res-auth&gt;Container&lt;/res-auth&gt;&#xa;             &lt;res-sharing-scope&gt;Shareable&lt;/res-sharing-scope&gt;&#xa;             &lt;injection-target&gt;ts&lt;/injection-target&gt;&#xa;         &lt;/resource-ref&gt;&#xa;         &lt;security-identity&gt;&#xa;            &lt;run-as&gt;&#xa;               &lt;role-name&gt;bankTeller&lt;/role-name&gt;&#xa;            &lt;/run-as&gt;&#xa;         &lt;/security-identity&gt;&#xa;      &lt;/session&gt;&#xa;      &lt;session&gt;&#xa;         &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;         &lt;remote&gt;org.jboss.ejb3.test.bank.Bank&lt;/remote&gt;&#xa;         &lt;ejb-class&gt;org.jboss.ejb3.test.bank.BankBean&lt;/ejb-class&gt;&#xa;         &lt;session-type&gt;Stateful&lt;/session-type&gt;&#xa;         &lt;transaction-type&gt;Container&lt;/transaction-type&gt;&#xa;         &lt;env-entry&gt;&#xa;            &lt;env-entry-name&gt;id&lt;/env-entry-name&gt;&#xa;            &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;&#xa;            &lt;env-entry-value&gt;5678&lt;/env-entry-value&gt;&#xa;         &lt;/env-entry&gt;&#xa;         &lt;resource-ref&gt;&#xa;            &lt;res-ref-name&gt;java:DefaultDS&lt;/res-ref-name&gt;&#xa;            &lt;res-type&gt;javax.sql.DataSource&lt;/res-type&gt;&#xa;            &lt;res-auth&gt;Container&lt;/res-auth&gt;&#xa;            &lt;res-sharing-scope&gt;Shareable&lt;/res-sharing-scope&gt;&#xa;            &lt;injection-target&gt;customerDb&lt;/injection-target&gt;&#xa;         &lt;/resource-ref&gt;&#xa;         &lt;interceptor&gt;org.jboss.ejb3.test.bank.FirstInterceptor&lt;/interceptor&gt;&#xa;         &lt;interceptor&gt;org.jboss.ejb3.test.bank.SecondInterceptor&lt;/interceptor&gt; &#xa;         &lt;callback-listener&gt;org.jboss.ejb3.test.bank.ExternalCallbackListener&lt;/callback-listener&gt;&#xa;      &lt;/session&gt;&#xa;   &lt;/enterprise-beans&gt;&#xa;   &lt;assembly-descriptor&gt;&#xa;      &lt;callback&gt;&#xa;         &lt;annotation&gt;PostConstruct&lt;/annotation&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;postConstruct&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/callback&gt; &#xa;      &lt;remove-list&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;remove&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/remove-list&gt; &#xa;      &lt;init-list&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;init&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/init-list&gt; &#xa;      &lt;security-role&gt;&#xa;         &lt;role-name&gt;bankCustomer&lt;/role-name&gt;&#xa;      &lt;/security-role&gt;&#xa;      &lt;security-role&gt;&#xa;         &lt;role-name&gt;bankTeller&lt;/role-name&gt;&#xa;      &lt;/security-role&gt;&#xa;      &lt;method-permission&gt;&#xa;         &lt;role-name&gt;bankCustomer&lt;/role-name&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;greetChecked&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/method-permission&gt;&#xa;      &lt;method-permission&gt;&#xa;         &lt;unchecked/&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;greetUnchecked&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/method-permission&gt;&#xa;      &lt;method-permission&gt;&#xa;         &lt;role-name&gt;bankTeller&lt;/role-name&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;getCustomerId&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;storeCustomerId&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/method-permission&gt;&#xa;      &lt;container-transaction&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;greetWithNotSupportedTransaction&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;         &lt;trans-attribute&gt;NotSupported&lt;/trans-attribute&gt;&#xa;      &lt;/container-transaction&gt;&#xa;      &lt;container-transaction&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;greetWithRequiredTransaction&lt;/method-name&gt;&#xa;            &lt;method-params&gt;&#xa;               &lt;method-param&gt;java.lang.String&lt;/method-param&gt;&#xa;            &lt;/method-params&gt;&#xa;         &lt;/method&gt;&#xa;         &lt;trans-attribute&gt;Required&lt;/trans-attribute&gt;&#xa;      &lt;/container-transaction&gt;&#xa;      &lt;container-transaction&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Bank&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;*&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;         &lt;trans-attribute&gt;Required&lt;/trans-attribute&gt;&#xa;      &lt;/container-transaction&gt;&#xa;      &lt;exclude-list&gt;&#xa;         &lt;method&gt;&#xa;            &lt;ejb-name&gt;Teller&lt;/ejb-name&gt;&#xa;            &lt;method-name&gt;excludedMethod&lt;/method-name&gt;&#xa;         &lt;/method&gt;&#xa;      &lt;/exclude-list&gt;&#xa;   &lt;/assembly-descriptor&gt;&#xa;&lt;/ejb-jar&gt;"/>
</node>
</node>
<node CREATED="1159218370576" ID="Freemind_Link_1028463104" MODIFIED="1159218380076" TEXT="configuration by exception"/>
</node>
<node CREATED="1159213980314" ID="Freemind_Link_580781296" MODIFIED="1159214001078" POSITION="right" TEXT="jboss" VSHIFT="-34">
<node CREATED="1159214065885" ID="Freemind_Link_987584489" MODIFIED="1159214089449" TEXT="can mix hibrnate and ejb entities"/>
</node>
<node CREATED="1159218507433" ID="Freemind_Link_961942290" MODIFIED="1159218511523" POSITION="left" TEXT="exceptions"/>
<node CREATED="1159549847602" ID="Freemind_Link_1547040305" MODIFIED="1159549863943" POSITION="right" TEXT="transactions" VSHIFT="-359">
<node CREATED="1159549875457" ID="Freemind_Link_74664118" MODIFIED="1159549884709" TEXT="default is REQUIRED"/>
<node CREATED="1159549886369" ID="Freemind_Link_1976916836" MODIFIED="1159549888967" TEXT="types">
<node CREATED="1159549889529" ID="Freemind_Link_1621702359" MODIFIED="1159550065774" TEXT="REQUIRED">
<icon BUILTIN="full-1"/>
<node CREATED="1159549970124" ID="Freemind_Link_1465697296" MODIFIED="1159549982945" TEXT="If the caller method is already inside a transaction, the &#xa;transaction is used; if not, a new transaction is generated."/>
</node>
<node CREATED="1159549894417" ID="Freemind_Link_1534419697" MODIFIED="1159550068275" TEXT="MANDATORY">
<icon BUILTIN="full-2"/>
<node CREATED="1159549999845" ID="Freemind_Link_354007583" MODIFIED="1159550010228" TEXT="The annotated method must be invoked inside a transaction (i.e., &#xa;the caller must have a transaction already). Otherwise, an error is thrown."/>
</node>
<node CREATED="1159549898401" ID="Freemind_Link_47286482" MODIFIED="1159550070480" TEXT="REQUIRESNEW">
<icon BUILTIN="full-3"/>
<node CREATED="1159550036021" ID="Freemind_Link_1761113198" MODIFIED="1159550060108" TEXT="The annotated method is executed within a newly generated &#xa;transaction. If the caller method is already in a transaction, it is suspended."/>
</node>
<node CREATED="1159549915426" ID="Freemind_Link_1645581353" MODIFIED="1159550073089" TEXT="SUPPORTS">
<icon BUILTIN="full-4"/>
<node CREATED="1159550094191" ID="Freemind_Link_353897564" MODIFIED="1159550104168" TEXT="If the annotated method is called within a transaction, the &#xa;transaction is used. If it is called without a transaction, no transaction will be created."/>
</node>
<node CREATED="1159549928946" ID="Freemind_Link_963419350" MODIFIED="1159550075312" TEXT="NOT_SUPPORTED">
<icon BUILTIN="full-5"/>
<node CREATED="1159550148310" ID="Freemind_Link_779053624" MODIFIED="1159550149785" TEXT="If the annotated method is called within a transaction, an error will be thrown."/>
</node>
</node>
<node CREATED="1159550298670" ID="Freemind_Link_1193652932" MODIFIED="1159550302089" TEXT="rollback">
<node CREATED="1159550303077" ID="Freemind_Link_1334799425" MODIFIED="1159550318161" TEXT="A transaction fails when the application throws a RuntimeException, &#xa;which is typically database related, or an ApplicationException."/>
</node>
<node CREATED="1159555976532" ID="Freemind_Link_250740808" MODIFIED="1159555978448" TEXT="default if no transaction demarcation is specified enterprise beans use container-managed transaction demarcation.  "/>
</node>
</node>
</map>