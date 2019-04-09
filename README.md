# Font Sizer

## To Do Items

1. listening to changes made to font sizes

[Done?] 1. Allow plugin to be enabled before project is fully indexed (i.e., we're not dependent on the state of the code)
   * Think this works now? (with FontAction::update)
   
[ ] 1. Update status bar (description) to indicate next font size if they click the icon

[ ] 1. Validation for initial sizes and adjustments (check and fix)

[ ] 1. Refactor State to be immutable, with "adjustSize" and "adjustDelta" that returns the modified version.

[ ] 1. Plugin settings dialog: change delta value

[ ] 1. Allow "UI" font size to be changed separately from editor/console font size
    * Also put this as an option: "adjust both? adjust editor/console only?

[ ] 1. Reset on exit and/or don't restore upon load?
    * Requires a "restore" button

[Done] 1. Add donate link
    `<a href='https://ko-fi.com/U7U48BNU'>Support Me on Ko-fi</a>`
