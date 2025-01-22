<div class="main-content">
    <%-- Default content --%>
    <!-- div id="default-content">
        <h2>Process Management System</h2>
        <p>Select an option from the sidebar to view details.</p>
    </div-->

    <%-- Existing Sheets View --%>
    <div id="existing-sheets-content" style="display: none;">
        <%@ include file="/components/open-existing-sheet.jsp" %>
    </div>

    <%-- Single Sheet View --%>
    <div id="single-sheet-content" style="display: none;">
        <%@ include file="/components/open-single-sheet.jsp" %>
    </div>
</div>
