<div class="example" id="demo-effect-slidedown" onclick="new Effect.SlideDown('userSettings')">
    <div style="height:10px;">
        <span>Settings</span>
    </div>

</div>

    <div id="userSettings">
        <table>
            <tr>
                <td>
                    <a href="/gweb/grouter/select.do">Settings</a>
                </td>
                <td>
                    <a href="/gweb/security/logedOut.do">Log out</a>
                </td>
                <td>
                    <a href="/gweb/security/login.do">Log in</a>

                </td>
                <td>
                    <a href="">User </a>
                </td>

            </tr>
        </table>
    </div>

    <a href="#" onclick="Element.hide('userSettings'); return true;">Hide</a>
    <a href="#" onclick="Element.show('userSettings'); return false;">Show</a>



