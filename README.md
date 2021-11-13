# Forgiac
 A reflection based forge wrapper. 

# WIP: Planned features
1) Virtual install environment support

## Compatibility: 
1.5.2-7.8.0.684+ (Tested so far) 

## Command Line arguments
<h3>Help commands:</h3>
<table>
        <tr>
                <th>Command</th>
                <th>Parameters</th>
                <th>Discription</th>
        </tr>
        <tr>
                <td><nobr>--mk_manifest</nobr></td>
                <td>Folder</td>
                <td>Creates a manifest file in a given directory</td>
        </tr>
        <tr>
                <td><nobr>--.minecraft</nobr></td>
                <td>Folder</td>
                <td>The path towards the location of a .minecraft directory</td>
        </tr>
        <tr>
                <td><nobr>--help</nobr></td>
                <td></td>
                <td>Shows this dialogue</td>
        </tr>
        <tr>
                <td><nobr>--log</nobr></td>
                <td>File</td>
                <td>Specifies the location of a log file</td>
        </tr>
        <tr>
                <td><nobr>--no_gui</nobr></td>
                <td></td>
                <td>Disables some of the gui elements</td>
        </tr>
        <tr>
                <td><nobr>--version</nobr></td>
                <td></td>
                <td>Shows build information</td>
        </tr>
        <tr>
                <td><nobr>--mk_release</nobr></td>
                <td></td>
                <td>Creates help files that allows you to release this in a repository</td>
        </tr>
        <tr>
                <td><nobr>--lock</nobr></td>
                <td></td>
                <td>Locks in the given set of values</td>
        </tr>
        <tr>
                <td><nobr>--html</nobr></td>
                <td></td>
                <td>Like the help command, but prints in html</td>
        </tr>
        <tr>
                <td><nobr>--virtual</nobr></td>
                <td>version&nbspfolder<br/>library&nbspfolder</td>
                <td>Used to install forge in launcher environments that don't emulate the vanilla launcher's file structure</td>
        </tr>
        <tr>
                <td><nobr>--installer</nobr></td>
                <td>Folder</td>
                <td>The path towards the location of a forge installer jar</td>
        </tr>
</table>

## Exit codes 
<table>
</table>
    <tr>
        <th>code</th>
        <th>Discription</th>
    </tr>
    <tr>
        <td>-1000</td>
        <td>Compatibility issue, will only pop up on Windows XP and earlier when using the --virtual parameter.</td>
    </tr>
    <tr>
        <td>0</td>
        <td>Forge was installed without issue</td>
    </tr>
    <tr>
        <td>100</td>
        <td>User cancelled request</td>
    </tr>
</table>