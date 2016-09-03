<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
    customBasepath="%{contextz}" />
</head>
<body>
<div class="list-icon">
<div class="clear"></div>
<div class="head">Setting</div>
</div>
<div class="clear"></div>
<div style="margin-top: 20px" align="center">
<table width="98%" cellspacing="0" cellpadding="20" border="0"
    class="settingsUI">
    <tbody>
        <tr>

            <td>
            <table width="100%" cellspacing="0" cellpadding="0" border="0">
                <tbody>
                    <tr>
                        <td class="settingsTabHeader">Users &amp; Access Management</td>
                    </tr>
                    <tr>
                        <td class="settingsIconDisplay small">
                        <table width="100%" cellspacing="0" cellpadding="5" border="0">
                            <tbody>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="0" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Users"
                                                    alt="Users" src="images/themes/ico-users.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Users </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage users who can
                                                access vtiger CRM</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Roles"
                                                    alt="Roles" src="images/themes/ico-roles.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Roles </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Set up hierarchy of
                                                roles and assign to the users</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Profiles"
                                                    alt="Profiles" src="images/themes/ico-profile.gif"> </a>
                                                </td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Profiles </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage user-specific
                                                modules access to different Roles</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Groups"
                                                    alt="Groups" src="images/themes/ico-groups.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Groups </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage different types
                                                of teams based on roles, users, and profiles</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Sharing Access" alt="Sharing Access"
                                                    src="images/themes/shareaccess.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Sharing Access </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage module sharing
                                                rules &amp; custom sharing rules</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Fields Access"
                                                    alt="Fields Access" src="images/themes/orgshar.gif">
                                                </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Fields Access </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Define global
                                                field-level access in each module</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Audit Trails"
                                                    alt="Audit Trails" src="images/themes/audit.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Audit Trails </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Display data opertions
                                                performed by users</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="User Login History" alt="User Login History"
                                                    src="images/themes/set-IcoLoginHistory.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                User Login History </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Display login history of
                                                users</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="settingsTabHeader">Studio</td>
                    </tr>
                    <tr>
                        <td class="settingsIconDisplay small">
                        <table width="100%" cellspacing="0" cellpadding="10" border="0">
                            <tbody>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Module Manager" alt="Module Manager"
                                                    src="images/themes/vtlib_modmng.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Module Manager </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage module behavior
                                                inside vtiger CRM</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Picklist Editor" alt="Picklist Editor"
                                                    src="images/themes/picklist.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Picklist Editor </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Customize Picklist
                                                values in each module</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Picklist Dependency Setup"
                                                    alt="Picklist Dependency Setup"
                                                    src="images/themes/picklistdependency.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Picklist Dependency Setup </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Setup Dependency between
                                                Picklist values in each module</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Menu Editor"
                                                    alt="Menu Editor" src="images/themes/menueditor.png">
                                                </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Menu Editor </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Customize Menu Sequence
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                                <tr>
                                </tr>
                            </tbody>
                        </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="settingsTabHeader">Communication Templates</td>
                    </tr>
                    <tr>
                        <td class="settingsIconDisplay small">
                        <table width="100%" cellspacing="0" cellpadding="10" border="0">
                            <tbody>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Notification Schedulers"
                                                    alt="Notification Schedulers"
                                                    src="images/themes/notification.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Notification Schedulers </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage Notifications
                                                that will alert in case of important actions</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Inventory Notifications"
                                                    alt="Inventory Notifications"
                                                    src="images/themes/inventory.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Inventory Notifications </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Change Settings of
                                                Inventory related Notifications</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="E-mail Templates" alt="E-mail Templates"
                                                    src="images/themes/ViewTemplate.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                E-mail Templates </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage templates for
                                                E-Mail module</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Company Details" alt="Company Details"
                                                    src="images/themes/company.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Company Details </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Specify business address
                                                of company</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Mail Merge"
                                                    alt="Mail Merge" src="images/themes/mailmarge.gif"> </a>
                                                </td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Mail Merge </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage templates for
                                                Mail Merging</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="settingsTabHeader">Other Settings</td>
                    </tr>
                    <tr>
                        <td class="settingsIconDisplay small">
                        <table width="100%" cellspacing="0" cellpadding="10" border="0">
                            <tbody>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Currencies"
                                                    alt="Currencies" src="images/themes/currency.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Currencies </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage international
                                                currencies and exchange rates</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Tax Calculations" alt="Tax Calculations"
                                                    src="images/themes/taxConfiguration.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Tax Calculations </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage taxes and the
                                                corresponding tax rates</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Outgoing Server" alt="Outgoing Server"
                                                    src="images/themes/ogmailserver.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Outgoing Server </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Configure Outgoing Mail
                                                Server details</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Proxy Server"
                                                    alt="Proxy Server" src="images/themes/proxy.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Proxy Server </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Configure proxies to
                                                access RSS feeds through Internet</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Backup Server"
                                                    alt="Backup Server" src="images/themes/backupserver.gif">
                                                </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Backup Server </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Specify database backup
                                                server details</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Announcements"
                                                    alt="Announcements" src="images/themes/announ.gif"> </a>
                                                </td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Announcements </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Manage company wide
                                                announcements</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Default Module View" alt="Default Module View"
                                                    src="images/themes/set-IcoTwoTabConfig.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Default Module View </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Set Default Detail View
                                                for All Modules</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Inventory : Terms &amp; Conditions"
                                                    alt="Inventory : Terms &amp; Conditions"
                                                    src="images/themes/terms.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Inventory : Terms &amp; Conditions </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Specify Terms and
                                                Conditions for quotes, orders, and invoices</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Customize Record Numbering"
                                                    alt="Customize Record Numbering"
                                                    src="images/themes/settingsInvNumber.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Customize Record Numbering </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Module Entity Number
                                                customization</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Mail Converter" alt="Mail Converter"
                                                    src="images/themes/mailScanner.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Mail Converter </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Configure mailbox for
                                                scanning</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Workflows"
                                                    alt="Workflows" src="images/themes/settingsWorkflow.png">
                                                </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Workflows </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Create and edit
                                                workflows for vtiger</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Customer Portal" alt="Customer Portal"
                                                    src="images/themes/portal_icon.png"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Customer Portal </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Allows you to Configure
                                                Customer Portal Plugin</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%" valign="top">

                                    <table style="margin: 0 0 20px 0" width="100%" cellspacing="0"
                                        cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0"
                                                    title="Configuration Editor" alt="Configuration Editor"
                                                    src="images/themes/migrate.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Configuration Editor </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Update configuration
                                                file of the application</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="ModTracker"
                                                    alt="ModTracker"
                                                    src="images/themes/set-IcoLoginHistory.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                ModTracker </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Select modules for
                                                tracking</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Scheduler"
                                                    alt="Scheduler" src="images/themes/audit.gif"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Scheduler </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Allows you to Configure
                                                Cron Task</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                    <td width="25%" valign="top">

                                    <table width="100%" cellspacing="0" cellpadding="5" border="0">
                                        <tbody>
                                            <tr>
                                                <td width="16%" rowspan="2" valign="top"><a href="#">
                                                <img width="48" height="48" border="0" title="Webforms"
                                                    alt="Webforms" src="images/themes/Webform.png"> </a></td>
                                                <td width="84%" valign="top" class="big"><a href="#">
                                                Webforms </a></td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="small">Allows you to manage
                                                Webforms</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    </td>
                                </tr>
                                <tr>
                                </tr>
                            </tbody>
                        </table>
                        </td>
                    </tr>
                </tbody>
            </table>
            </td>
        </tr>
    </tbody>
</table>
</div>
</body>
</html>