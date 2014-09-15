grails-umeditor
==============

Grails UMeditor Plugin for Baidu UEditor Mini(http://ueditor.baidu.com/website/umeditor.html)


## Installation

To install the plugin add a dependency to BuildConfig.groovy:
~~~~~~~~~~~
compile ":umeditor:1.2.2"
~~~~~~~~~~~

## Configuration

Configure toolbar buttions, use predefined toolbar types: mini, compcat, default, full.

~~~~~~~~~~~
    <umeditor:toolbar type="compact"/>
~~~~~~~~~~~

Or customize it, 

~~~~~~~~~~~
<umeditor:toolbar>
    source | undo redo | bold italic underline strikethrough | superscript subscript |,
    forecolor backcolor | removeformat |,
    | justifyleft justifycenter justifyright justifyjustify |,
    link unlink | emotion image video | map,
    | horizontal print preview fullscreen,
    drafts,
    formula
</umeditor:toolbar>
~~~~~~~~~~~

## Usage

Include required resources in page header

~~~~~~~~~~~
<r:require module="jquery"/>
<umeditor:resources/>
~~~~~~~~~~~

Declare editor in form
~~~~~~~~~~~
<g:form>
    <umeditor:editor id="body" style="width:100%;height:360px;">Hello World</umeditor:editor>
</g:form>
~~~~~~~~~~~

