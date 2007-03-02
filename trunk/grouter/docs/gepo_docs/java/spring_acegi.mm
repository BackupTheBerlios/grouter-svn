<map version="0.8.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1162464980927" ID="Freemind_Link_332061614" MODIFIED="1162464986907" TEXT="spring acegi">
<node CREATED="1162464995813" ID="Freemind_Link_1910224010" MODIFIED="1162465000548" POSITION="left" TEXT="authorization">
<node CREATED="1162465003200" ID="Freemind_Link_25747032" MODIFIED="1162465005572" TEXT="role base"/>
</node>
<node CREATED="1162469709026" ID="Freemind_Link_1711689233" MODIFIED="1162469714901" POSITION="right" TEXT="FilterchaingProxy">
<node CREATED="1162469715297" ID="Freemind_Link_1675496950" MODIFIED="1162469731749" TEXT="&lt;html&gt;&lt;img src=&quot;acegi_filtechainproxy.gif&quot;&gt;"/>
</node>
<node CREATED="1162464989542" ID="_" MODIFIED="1162464994383" POSITION="left" TEXT="authentication">
<node CREATED="1162465007840" ID="Freemind_Link_1826177623" MODIFIED="1162465012612" TEXT="user / password"/>
</node>
<node CREATED="1162469771275" ID="Freemind_Link_1512160642" MODIFIED="1162469780623" POSITION="right" TEXT="Filters">
<node CREATED="1162465168911" HGAP="50" ID="Freemind_Link_1216679537" MODIFIED="1162469815538" TEXT="&lt;html&gt;&lt;img src=&quot;spring_acegi_filters.gif&quot;&gt;" VSHIFT="-182">
<node CREATED="1162465172439" ID="Freemind_Link_299005439" MODIFIED="1162465173841" TEXT="AuthenticationProcessingFilter">
<node CREATED="1162465174727" ID="Freemind_Link_1834246935" MODIFIED="1162465174727" TEXT="">
<node CREATED="1162465197342" ID="Freemind_Link_1598218411" MODIFIED="1162465197342" TEXT="handles the Authentication Request Check (&#x201c;logging into the application&#x201d;). It uses the AuthenticationManager to do its work.">
<node CREATED="1162803075261" ID="Freemind_Link_717830120" MODIFIED="1162803156492" TEXT="Use alwaysUseDefaultTargetUrl to set wether we should always go to a specific &#xa;login page after sucessful login or if we should go to the requested resource."/>
</node>
</node>
</node>
<node CREATED="1162465220521" ID="Freemind_Link_787335385" MODIFIED="1162465221689" TEXT="HttpSessionContextIntegrationFilter">
<node CREATED="1162465212841" ID="Freemind_Link_1653300808" MODIFIED="1162465214280" TEXT="he HttpSessionContextIntegrationFilter maintains the Authentication object between various requests and passes it around to the AuthenticationManager and the AccessDecisionManager when needed;"/>
</node>
<node CREATED="1162465272028" ID="Freemind_Link_228645331" MODIFIED="1162465273383" TEXT="ExceptonTranslationFilter">
<node CREATED="1162465262477" ID="Freemind_Link_1610490363" MODIFIED="1162465265379" TEXT="The ExceptonTranslationFilter performs the Existing Authentication Check, handles security exceptions and takes the appropriate action. This action can be either spawning the authentication dialog (a.k.a. the login form) or returning the appropriate HTTP security error code. ExceptonTranslationFilter depends on the next filter, FilterSecurityInterceptor, to do its work."/>
</node>
<node CREATED="1162465303636" ID="Freemind_Link_138497189" MODIFIED="1162465304821" TEXT="FilterSecurityInterceptor">
<node CREATED="1162465296108" ID="Freemind_Link_965979587" MODIFIED="1162465297499" TEXT="FilterSecurityInterceptor manages the Restricted Acces Check,and the Authorisation check. It knows which resources are secure and which roles have access to them. FilterSecurityInterceptor uses the AuthenticationManager and  AccessDecisionManager to do its work."/>
</node>
<node CREATED="1162469775267" ID="Freemind_Link_1655068108" MODIFIED="1162469775267" TEXT=""/>
</node>
</node>
<node CREATED="1162539738558" ID="Freemind_Link_493617318" MODIFIED="1162539761490" POSITION="left" TEXT="central objects">
<node CREATED="1162539762390" ID="Freemind_Link_455430900" MODIFIED="1162539764203" TEXT="UserDetails">
<node CREATED="1162539744341" ID="Freemind_Link_1865435895" MODIFIED="1162539927270" TEXT="UserDetails as the adapter between your own user database and &#xa;what Acegi Security needs inside the SecurityContextHolder"/>
</node>
<node CREATED="1162539976873" ID="Freemind_Link_381063950" MODIFIED="1162539978973" TEXT="SecurityContextHolder">
<node CREATED="1162539955072" ID="Freemind_Link_1262660217" MODIFIED="1162539973616" TEXT="Inside the SecurityContextHolder we store details of the &#xa;principal currently interacting with the application"/>
<node CREATED="1162540046491" ID="Freemind_Link_1167168" MODIFIED="1162540139131" TEXT="This is where we store details of the present security context of the &#xa;application, which includes details of the principal currently using &#xa;the application. By default the SecurityContextHolder uses a ThreadLocal &#xa;to store these details, which means that the security context is always &#xa;available to methods in the same thread of execution, even if the security &#xa;context is not explicitly passed around as an argument those methods.">
<node CREATED="1162764407630" ID="Freemind_Link_323197708" MODIFIED="1162764407630" TEXT=""/>
</node>
<node CREATED="1162540084292" ID="Freemind_Link_156199353" MODIFIED="1162540087531" TEXT=""/>
</node>
<node CREATED="1162765554732" ID="Freemind_Link_1630472033" MODIFIED="1162765566192" TEXT="Authentication">
<node CREATED="1162764524865" ID="Freemind_Link_1292977313" MODIFIED="1162765591054" TEXT="Authentication">
<node CREATED="1162764538433" ID="Freemind_Link_32129967" MODIFIED="1162764551461" TEXT="created by client cde"/>
<node CREATED="1162764600131" ID="Freemind_Link_1692985038" MODIFIED="1162764602071" TEXT="The Authentication interface which holds three important objects. The first object is the principal, which identifies the caller (user). The second object are the credentials that provide proof of the identity of the caller. For traditional logins, this is the username&apos;s respective password. The final object contained by the Authentication interface is and array of the authorities granted to the principal."/>
</node>
<node CREATED="1162764341738" ID="Freemind_Link_1597510395" MODIFIED="1162765591052" TEXT="AuthenticationManager">
<arrowlink DESTINATION="Freemind_Link_1292977313" ENDARROW="Default" ENDINCLINATION="-75;30;" ID="Freemind_Arrow_Link_1217032903" STARTARROW="None" STARTINCLINATION="-49;-19;"/>
<node CREATED="1162764358331" ID="Freemind_Link_462643728" MODIFIED="1162764360751" TEXT="AuthenticationManager specifies a single method, authenticate.  public Authentication authenticate(Authentication authentication) throws AuthenticationException; ">
<node CREATED="1162764411950" ID="Freemind_Link_186543191" MODIFIED="1162764414554" TEXT="BadCredentialsException, DisabledException, and LockedException"/>
</node>
<node CREATED="1162764519304" HGAP="21" ID="Freemind_Link_1523666818" MODIFIED="1162803245934" TEXT="ProviderManager." VSHIFT="105">
<node CREATED="1162764737484" ID="Freemind_Link_79737939" MODIFIED="1162764745608" TEXT="Wraps AuthenticationManager"/>
<node CREATED="1162764807877" ID="Freemind_Link_237871371" MODIFIED="1162764809561" TEXT="DaoAuthenticationProvide">
<node CREATED="1162765017234" ID="Freemind_Link_1460747242" MODIFIED="1162765018958" TEXT="it delegates to an implementation of the AuthenticationDao interface, which has a single method, loadUserByUsername"/>
<node CREATED="1162765033460" ID="Freemind_Link_1433982699" MODIFIED="1162765035087" TEXT="InMemoryDaoImpl"/>
<node CREATED="1162765038018" ID="Freemind_Link_1999172464" MODIFIED="1162765047830" TEXT="Create your own"/>
</node>
<node CREATED="1162764816685" ID="Freemind_Link_1321179329" MODIFIED="1162764819013" TEXT="JAAS"/>
<node CREATED="1162764820653" ID="Freemind_Link_423868040" MODIFIED="1162764823616" TEXT="JBoss"/>
<node CREATED="1162765147186" ID="Freemind_Link_1414592072" MODIFIED="1162765156326" TEXT="Example config">
<node CREATED="1162765157330" ID="Freemind_Link_1519205123" MODIFIED="1162765171423" TEXT=" &lt;bean id=&quot;authenticationManager&quot; class=&quot;net.sf.acegisecurity.providers.ProviderManager&quot;&gt;&#xa;        &lt;property name=&quot;providers&quot;&gt;&#xa;            &lt;list&gt;&#xa;                &lt;ref bean=&quot;daoAuthenticationProvider&quot;/&gt;&#xa;                &lt;ref local=&quot;anonymousAuthenticationProvider&quot;/&gt;&#xa;            &lt;/list&gt;&#xa;        &lt;/property&gt;&#xa;    &lt;/bean&gt;&#xa;&#xa;    &lt;bean id=&quot;daoAuthenticationProvider&quot; class=&quot;net.sf.acegisecurity.providers.dao.DaoAuthenticationProvider&quot;&gt;&#xa;        &lt;property name=&quot;authenticationDao&quot;&gt;&#xa;            &lt;ref local=&quot;memoryAuthenticationDao&quot;/&gt;&#xa;        &lt;/property&gt;&#xa;    &lt;/bean&gt;&#xa;&#xa;    &lt;!-- When we have no persistence provider can provide a realm - use this --&gt;&#xa;    &lt;bean id=&quot;memoryAuthenticationDao&quot; class=&quot;net.sf.acegisecurity.providers.dao.memory.InMemoryDaoImpl&quot;&gt;&#xa;        &lt;property name=&quot;userMap&quot;&gt;&#xa;            &lt;value&gt;admin=admin,ROLE_ADMIN,ROLE_USER&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;    &lt;/bean&gt;"/>
</node>
<node CREATED="1162803246744" ID="Freemind_Link_1561033641" MODIFIED="1162803248509" TEXT="A provider is essentially a repository of usernames with corresponding passwords and roles. The authenticationManager will stop iterating through the list of providers once a user is successfully authenticated. In practice, you may have two or three providers; for example, one provider could access an Active Directory for employee credentials, while your second provider might access a database for customer credentials. You will most often need an anonymousAuthenticationProvider because you need it to allow access to pages that do not requiring authentication to access, such as the login page or the home page. The demonstration application for this article uses a memory provider and an anonymous provider. Once you get this simple application working, you probably want to add a JDBC or LDAP provider."/>
</node>
</node>
</node>
<node CREATED="1162765702777" ID="Freemind_Link_342249314" MODIFIED="1162765705365" TEXT="Authorization">
<node CREATED="1162765755337" ID="Freemind_Link_397492334" MODIFIED="1162765759283" TEXT="AccessDecisionManager,">
<node CREATED="1162765815986" ID="Freemind_Link_197445044" MODIFIED="1162765816990" TEXT="ConsensusBased, UnanimousBased, and AffirmativeBased."/>
</node>
<node CREATED="1162765810618" ID="Freemind_Link_425091675" MODIFIED="1162765814558" TEXT="Voting"/>
</node>
</node>
<node CREATED="1162539165786" ID="Freemind_Link_836431502" MODIFIED="1162539171445" POSITION="left" TEXT="deployment">
<node CREATED="1162539172156" ID="Freemind_Link_948162444" MODIFIED="1162539213753" TEXT="no need to configure a JAAS domain or place a jar in a server lib"/>
<node CREATED="1162539215997" ID="Freemind_Link_1002267857" MODIFIED="1162539243194" TEXT="just include the jar files in e.g. a war"/>
</node>
</node>
</map>
