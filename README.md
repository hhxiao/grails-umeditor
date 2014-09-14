grails-umeditor
==============

Grails UMeditor Plugin for http://ueditor.baidu.com/website/umeditor.html


## Installation

To install the plugin add a dependency to BuildConfig.groovy:
~~~~~~~~~~~
compile ":umeditor:1.2.2"
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

