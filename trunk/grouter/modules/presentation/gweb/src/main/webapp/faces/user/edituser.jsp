<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Nodes
    </title>

</head>
<body>

<div id="menuAction">

</div>

<div id="paragraph">
    Edit node.
</div>


<form id="mainForm" action="">
    <table>
        <tr>
            <td>
               Id
            </td>
            <td>
               <input name="id" disabled="true" />
            </td>
        </tr>
        <tr>
            <td>
              Name
            </td>
            <td>
               <input name="name"  /> 
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
               <input type="button" name="editNode" value="Save"  /> 
            </td>
        </tr>

    </table>

</form>
</body>
</html>
