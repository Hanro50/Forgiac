# Forgiac
 A reflection based forge wrapper. 

# WIP: Planned features

## Short term goals
1) Command line support. 
> Mostly the ability to specify a .minecraft folder and the location of a forge installer jar
2) manifest.json and version.json generator
> Mostly there to make it easier to redistribute this library with a custom launcher that can decode version.jsons and version manifest files. 

## Compatibility: 
1.8.9+ (Tested so far) 

## Command Line arguments
<table>
 <tr><th>command</th><th>description</th></tr>
 <tr><td>--.minecraft</td><td>The path towards the location of a .minecraft directory</td></tr>
 <tr><td>--help</td><td>Shows this information</td></tr>
 <tr><td>--no_gui</td><td>Disables some of the gui elements</td></tr>
 <tr><td>--version</td><td>Shows build information</td></tr>
 <tr><td>--lock</td><td>Locks in the given set of values</td></tr>
 <tr><td>--installer</td><td>The path towards the location of a forge installer jar</td></tr>
</table>
