# Task 1 - Test the buggy application - http://goalsec.com/exercise_2017/

### This is working copy of the actual submission
### TODO - remeber to convert to PDF before actual submit

# Bug list
## General
### credentials
* typo - "You" -> "Your"
* not sure - credentials are not capitalized
* strange commented line - <!--Argh!!! What am I doing???!!!!-->
* not valid behavior for unicode characters:
  ** č, ď, ľ, ĺ.. -> &
* not sure - from time to time - not the same results for the same inputs

### Name string
* input - &#72;&#101;&#108;&#108;&#111; -> Hello

## Security
### XSS - Cross-site Scripting
TODO - scenarios



