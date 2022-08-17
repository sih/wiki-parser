package datasoc.ltd.labs.pipeline;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import datasoc.ltd.labs.config.TemplateConfig;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author sih
 */
class PageParserIntegrationTest {

  private PageParser pageParser;
  private static final int ACDC_EXPECTED_CHILDREN = 243;
  private TemplateConfig acdcConfig;
  private URL adcPage;
  private static final ObjectMapper YAML_PARSER = new ObjectMapper(new YAMLFactory());

  @BeforeEach
  void setUp() throws Exception {
    acdcConfig = YAML_PARSER.readValue(this.getClass().getResource("/acdc.selectors.yaml"),
        TemplateConfig.class);
    adcPage = new URL("https://github.com/trustoverip/acdc/wiki/authentic-data-container");
    pageParser = new PageParser();

  }

  @Test
  void testElementsReturned() {
    List<String> childUrls = pageParser.getSubpages(acdcConfig);
    assertThat(childUrls).hasSize(ACDC_EXPECTED_CHILDREN);
  }

  @Test
  void testSubpageParses() {
    String expectedId = "authentic-data-container";
    String expectedTermId = "authentic-data-container";
    String expectedTermName = "authentic data container";
    String expectedGlossaryText = "A mechanism for conveying data that allows the authenticity of its content to be proved.";
    PageData data = pageParser.getSubpageElements(adcPage, acdcConfig);
    assertThat(data).isNotNull();
    assertThat(data.id()).isEqualTo(expectedId);
    assertThat(data.termId()).isEqualTo(expectedTermId);
    assertThat(data.termName()).isEqualTo(expectedTermName);
    assertThat(data.glossaryText()).isEqualTo(expectedGlossaryText);
  }

}