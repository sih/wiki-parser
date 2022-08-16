package datasoc.ltd.labs.config;

/**
 * @author sih
 */
public record SubpageConfig(
    String idSelector,
    String termIdSelector,
    String termNameSelector,
    String glossaryTextSelector,
    String createdAtSelector,
    String updatedAtSelector,
    int subpageTimeoutMs) {

}
