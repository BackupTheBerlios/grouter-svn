<map version="0.8.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1159769871492" ID="Freemind_Link_1344289819" MODIFIED="1159769879609" TEXT="spring">
<node CREATED="1159769881168" ID="_" MODIFIED="1159800970055" POSITION="right" TEXT="bean life cycle">
<node CREATED="1159801137544" ID="Freemind_Link_412544085" MODIFIED="1159801147124" TEXT="pre-destruction">
<node CREATED="1159801147624" ID="Freemind_Link_1459380688" MODIFIED="1159801149324" TEXT=" event is fired just before Spring destroys the bean  instance"/>
</node>
<node CREATED="1159801151913" ID="Freemind_Link_959446334" MODIFIED="1159801183765" TEXT="post-initialization ">
<node CREATED="1159801184306" ID="Freemind_Link_534343095" MODIFIED="1159801205132" TEXT="event is raised as soon as Spring finishes  setting all the property values on &#xa;the bean and finishes any dependency checks that you  configured it to perform"/>
</node>
<node CREATED="1159801337182" ID="Freemind_Link_61607481" MODIFIED="1159801703700" TEXT="  "/>
<node CREATED="1159801746482" ID="Freemind_Link_1289739503" MODIFIED="1159801753949" TEXT="two ways:">
<node CREATED="1159801601117" ID="Freemind_Link_843050456" MODIFIED="1159801708734" TEXT="implment e.g. interface InitializingBean">
<icon BUILTIN="full-1"/>
</node>
<node CREATED="1159801647310" ID="Freemind_Link_688771741" MODIFIED="1159801833879" TEXT="init is a method run on post - init life cycle&#xa;&lt;bean id=&quot;simpleBean&quot; class=&quot;com.lifecycle.SimpleBean&quot;  init-method=&quot;init&quot;&gt;">
<icon BUILTIN="full-2"/>
</node>
</node>
<node CREATED="1159802021793" ID="Freemind_Link_1677387634" MODIFIED="1159802029375" TEXT="the BeanNameAware interface">
<node CREATED="1159802030057" ID="Freemind_Link_1446556764" MODIFIED="1159802046117" TEXT="a way to get the name of the bea, good for logging"/>
</node>
<node CREATED="1159855156723" ID="Freemind_Link_1056376719" MODIFIED="1159855163612" TEXT="method lookup injection">
<node CREATED="1159855164637" ID="Freemind_Link_765199168" MODIFIED="1159855189282" TEXT="using cglib we can alter a method at runtime"/>
<node CREATED="1159855190646" ID="Freemind_Link_161316913" MODIFIED="1159855218212" TEXT="performance heavy"/>
<node CREATED="1159855223296" ID="Freemind_Link_283658370" MODIFIED="1159855243868" TEXT="good for bean with differnet life cycles and for singelton and non singelton beans"/>
</node>
</node>
<node CREATED="1159769888828" ID="Freemind_Link_1226972517" MODIFIED="1159771004925" POSITION="left" TEXT="dependency injection">
<node CREATED="1159800771349" ID="Freemind_Link_490801463" MODIFIED="1159800797204" TEXT="notion of inheritence o properties inside beanfactory" VSHIFT="9"/>
<node CREATED="1159771439083" ID="Freemind_Link_1803501432" MODIFIED="1159773540860" TEXT="beanfactory">
<node CREATED="1159771443098" ID="Freemind_Link_1013397697" MODIFIED="1159771453645" TEXT="A BeanFactory is  responsible for managing &#xa;components and their dependencies."/>
<node CREATED="1159771611299" ID="Freemind_Link_32993180" MODIFIED="1159771612717" TEXT="XmlBeanFactory">
<node COLOR="#0033ff" CREATED="1159771728084" ID="Freemind_Link_24779299" MODIFIED="1159801057901" TEXT="XmlBeanFactory factory = new XmlBeanFactory(new FileSystemResource( &quot;conf/beans.xml&quot;)); &#xa;Oracle oracle = (Oracle)factory.getBean(&quot;oracle&quot;); &#xa;"/>
<node COLOR="#3333ff" CREATED="1159772375035" ID="Freemind_Link_5391767" MODIFIED="1159800631930" TEXT="&lt;!DOCTYPE beans PUBLIC &quot;-//SPRING//DTD BEAN//EN&quot;&#xa;&quot;http://www.springframework.org/dtd/spring-beans.dtd&quot;&gt; &#xa;&lt;beans&gt;&#xa;   &lt;bean id=&quot;renderer&quot;  class=&quot;com.StandardOutMessageRenderer&quot;/&gt;&#xa;&lt;/beans&gt;"/>
<node CREATED="1159771808679" ID="Freemind_Link_221728970" MODIFIED="1159800987855" TEXT="collection injection">
<node COLOR="#3333ff" CREATED="1159772929390" ID="Freemind_Link_112823471" MODIFIED="1159801028131" TEXT="  &lt;property name=&quot;list&quot;&gt; &#xa;          &lt;list&gt; &#xa;              &lt;value&gt;Hello World!&lt;/value&gt; &#xa;              &lt;ref local=&quot;oracle&quot;/&gt; &#xa;          &lt;/list&gt; &#xa;  &lt;/property&gt; &#xa;&lt;/bean&gt; "/>
</node>
</node>
<node CREATED="1159773234323" ID="Freemind_Link_1833587591" MODIFIED="1159773296105" TEXT="A bean must be uniqeue within its bean factory" VSHIFT="17">
<node CREATED="1159773250804" ID="Freemind_Link_258324080" MODIFIED="1159773280683" TEXT="id is used">
<icon BUILTIN="full-1"/>
</node>
<node CREATED="1159773260020" ID="Freemind_Link_1773682155" MODIFIED="1159773283206" TEXT="name">
<icon BUILTIN="full-2"/>
</node>
<node CREATED="1159773265692" ID="Freemind_Link_439287748" MODIFIED="1159773285767" TEXT="full class name">
<icon BUILTIN="full-3"/>
</node>
<node COLOR="#3333ff" CREATED="1159773389012" FOLDED="true" ID="Freemind_Link_1543478249" MODIFIED="1159800631921" TEXT="Alias name:&#xa;&lt;bean  id=&quot;name1&quot; name=&quot;name2,name3,name4&quot; class=&quot;java.lang.String&quot;/&gt; ">
<node CREATED="1159773393772" MODIFIED="1159773393772" TEXT="&lt;bean  id=&quot;name1&quot; name=&quot;name2,name3,name4&quot; class=&quot;java.lang.String&quot;/&gt;"/>
</node>
</node>
<node COLOR="#000000" CREATED="1159773542335" ID="Freemind_Link_250998519" MODIFIED="1159800170503" TEXT="all beans are by default singeltons">
<font NAME="SansSerif" SIZE="10"/>
<node COLOR="#3333ff" CREATED="1159799906116" ID="Freemind_Link_1797251992" MODIFIED="1159800631966" TEXT="Can be changed using:&#xa;&lt;bean id=&quot;nonSingleton&quot; class=&quot;java.lang.String&quot; singleton=&quot;false&quot;&gt; &#xa;&#xa;"/>
</node>
<node CREATED="1159800197240" ID="Freemind_Link_247637041" MODIFIED="1159800204860" TEXT="a bean can depend on another bean">
<node COLOR="#3333ff" CREATED="1159800210505" ID="Freemind_Link_574478373" MODIFIED="1159800631976" TEXT="&lt;bean id=&quot;A&quot; class=&quot;com.BeanA&quot; depends-on=&quot;b&quot;/&gt;&#xa;&lt;bean id=&quot;B&quot; class=&quot;com.apress.prospring.ch4.BeanB&quot;/&gt; "/>
</node>
</node>
<node CREATED="1159773049435" HGAP="58" ID="Freemind_Link_362943353" MODIFIED="1159773306889" TEXT="types" VSHIFT="30">
<node CREATED="1159771012370" HGAP="23" ID="Freemind_Link_1873381773" MODIFIED="1159773300885" TEXT="setter type" VSHIFT="11">
<node COLOR="#3333ff" CREATED="1159772179672" ID="Freemind_Link_1644078844" MODIFIED="1159800631999" TEXT="&lt;bean id=&quot;renderer&quot;  class=&quot;com.StandardOutMessageRenderer&quot;&gt;&#xa;    &lt;property name=&quot;messageProvider&quot;&gt;              &#xa;        &lt;ref local=&quot;provider&quot;/&gt;          &#xa;    &lt;/property&gt;      &#xa;&lt;/bean&gt; "/>
</node>
<node CREATED="1159771005337" ID="Freemind_Link_1485463204" MODIFIED="1159771011149" TEXT="constructor type">
<node COLOR="#3333ff" CREATED="1159772243300" ID="Freemind_Link_838034556" MODIFIED="1159800631983" TEXT="&lt;bean id=&quot;provider&quot; class=&quot;com.ConfigurableMessageProvider&quot;&gt;&#xa;   &lt;constructor-arg&gt;              &#xa;      &lt;value&gt;This is a configurable message&lt;/value&gt;          &#xa;  &lt;/constructor-arg&gt;      &#xa;&lt;/bean&gt; "/>
</node>
</node>
<node CREATED="1159800445630" ID="Freemind_Link_1830080759" MODIFIED="1159800449769" TEXT="checking dependencies">
<node CREATED="1159800450846" ID="Freemind_Link_19118195" MODIFIED="1159800467458" TEXT="simple - checks primitives"/>
<node CREATED="1159800468438" ID="Freemind_Link_80538899" MODIFIED="1159800479898" TEXT="object - checks object"/>
<node CREATED="1159800481623" ID="Freemind_Link_30053883" MODIFIED="1159800489453" TEXT="all - checks all">
<node COLOR="#0000ff" CREATED="1159800517144" ID="Freemind_Link_1167325764" MODIFIED="1159800603403" TEXT="&lt;bean id=&quot;simpleBean3&quot; class=&quot;com.SimpleBean&quot; dependency-check=&quot;all&quot;&gt;">
<edge COLOR="#3333ff"/>
</node>
</node>
<node CREATED="1159800673325" ID="Freemind_Link_887601441" MODIFIED="1159800689185" TEXT="if failure -&gt; UnsatisfiedDependencyException"/>
</node>
</node>
<node CREATED="1159769899060" ID="Freemind_Link_1139864202" MODIFIED="1159856092551" POSITION="right" TEXT="PropertyEditors">
<node CREATED="1159856093059" ID="Freemind_Link_1384885477" MODIFIED="1159856114015" TEXT="a way to convert String types into typed Objects">
<node COLOR="#0000ff" CREATED="1159856120588" ID="Freemind_Link_905734221" MODIFIED="1159856189897" TEXT="&lt;bean id=&quot;builtInSample&quot; &#xa;          class=&quot;com.PropertyEditorBean&quot;&gt;&#xa;        &lt;property name=&quot;file&quot;&gt; &#xa;            &lt;value&gt;d:/tmp/test.txt&lt;/value&gt; &#xa;        &lt;/property&gt;"/>
</node>
</node>
<node CREATED="1159769904988" ID="Freemind_Link_1711767457" MODIFIED="1159769904988" POSITION="left" TEXT=""/>
<node CREATED="1159769911232" ID="Freemind_Link_1300985452" MODIFIED="1159769911232" POSITION="left" TEXT=""/>
<node CREATED="1159769913640" ID="Freemind_Link_1163076208" MODIFIED="1159855451905" POSITION="right" TEXT="FactoryBean">
<node CREATED="1159855452917" ID="Freemind_Link_796613608" MODIFIED="1159855461377" TEXT="a bean that acts as a factory for other beans."/>
<node CREATED="1159855699734" ID="Freemind_Link_47777379" MODIFIED="1159855724522" TEXT="use as an adaptor for classes that can not be created using &quot;new&quot;"/>
</node>
<node CREATED="1159769916073" ID="Freemind_Link_787239385" MODIFIED="1159770018559" POSITION="left" TEXT="negative"/>
<node CREATED="1159856390649" ID="Freemind_Link_1979823211" MODIFIED="1159856401783" POSITION="right" TEXT="ApplicationContext">
<node CREATED="1159856427978" ID="Freemind_Link_845281152" MODIFIED="1159856437902" TEXT="an extension of BeanFactory"/>
<node CREATED="1159856518610" ID="Freemind_Link_751719662" MODIFIED="1159856520054" TEXT="reducing the  amount of code you need to write in order to use Spring.  "/>
<node CREATED="1159856581171" ID="Freemind_Link_1635969256" MODIFIED="1159856607496" TEXT="supports the following features not present in a BeanFactory:  &#xa;&#x2022; Internationalization  &#xa;&#x2022; Event publication  &#xa;&#x2022; Resource management and access  &#xa;&#x2022; Additional lifecycle interfaces  &#xa;&#x2022; Improved automatic configuration of infrastructure components"/>
</node>
</node>
</map>
