class UmeditorGrailsPlugin {
    // the plugin version
    def version = "1.2.2_1"

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"

    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "Grails UMeditor Plugin"
    def author = "Hai-Hua Xiao"
    def authorEmail = "hhxiao@gmail.com"
    def description = '''\
UMeditor web WYSIWYG editor integration plugin.
'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/hhxiao/grails-umeditor"

    def license = "APACHE"
    def developers = []
    def scm = [url: "https://github.com/hhxiao/grails-umeditor/"]
}
