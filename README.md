Tmux IntelliJ Plugin
=====================

Plugin that provides Tmux integration with IntelliJ. First pass will be the ability to send data to a specific Tmux panel, which can allow for easy REPL integration with IntelliJ, although I'm sure it can be used for other things.

Usage
------

### Selecting the Pane to Send Text To ###

1. You must first select a the pane you want your text to go to through *Tools > Tmux > Select Pane*. This pane selection is persisted until you restart IntelliJ.


### Send Selected Text to Tmux ###

1. To paste text to the Tmux pane from here, you can select the *Send Selected to Tmux* action (*Tools > Tmux*, or through the right click menu), or through the shortcut key, Ctrl+B, V.

### Send Current Line to Tmux ###
1. To send the text from the current line your caret is on to a Tmux pane, you can select the *Send Current Line To Tmux* action (*Tools > Tmux*, or through the right click menu), or through the shortcut key, Ctrl+B, L.



Contribution
------------
Please feel free to fork and make push requests to contribute back!

