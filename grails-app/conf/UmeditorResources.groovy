import org.grails.plugin.umeditor.UmeditorConfig

def ver = UmeditorConfig.PLUGIN_VERSION

modules = {
    'umeditor' {
        dependsOn('jquery')
        resource id:'js', url:[plugin: 'umeditor', dir:"umeditor-${ver}", file:"umeditor.config.js"], disposition:'head', nominify: true
        resource id:'js', url:[plugin: 'umeditor', dir:"umeditor-${ver}", file:"umeditor.js"], disposition:'head', nominify: true
    }
}
