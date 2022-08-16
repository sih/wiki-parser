package datasoc.ltd.labs.config;

/**
 * @author sih
 */
public record TemplateConfig(String scope, String homepageUrl, String subpageCssSelector,
                             int pageTimeoutMs, SubpageConfig subpageConfig) {

}
