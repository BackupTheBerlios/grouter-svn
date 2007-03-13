<%@ page session="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

 <div id="logoBar">
        <img id="logo" src="/images/browser.png" alt="logo"/>
                <a href="/gweb/customer/list.do" >
              <img id="supportWeb" src="/images/configure.png" width="24" height="24" border="0" />
                    </a>
             <a id="vendorSettnings" href="/gweb/settings/edit.do">Inställningar</a>
            <a id="logout" href="/j_acegi_logout">Logga ut</a>
            <a id="personalSettings" href="">Inloggad :
            <a id="login" href="/gweb/auth/login.do">Logga in</a>
         
            </div>


<ul class="menu tabbed">

		<li>
            <a href="">Message</a>
        </li>

		<li>
            <a href="">Node</a>
        </li>
</ul>
