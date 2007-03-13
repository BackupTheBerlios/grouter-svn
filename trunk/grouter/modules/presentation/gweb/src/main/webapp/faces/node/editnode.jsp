<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk" %>


<f:view>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
    <title>
        Node
    </title>
    <link href='<c:out value="${requestScope.CONTEXT_PATH}"/>/css/main.css' rel="stylesheet"/>
</head>
<body>
                  kasdjhfladjs fladfadjlsfha dksjf
<h:form id="form1" >
    <table>

        <tr>
            <td>User ID:
                <h:inputText value="#{NodeBean.name}" required="true" id="id"/>
            </td>
            <td>
                <!-- h:message for="id" styleClass="error"/ -->
            </td>
        </tr>
        <tr>
            <th colspan=2>
                <h:commandButton value="Save" action="#{NodeBean.submitSave}"/>
            </th>
        </tr>
    </table>
</h:form>


</body>
</html>
</f:view>
