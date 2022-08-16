package datasoc.ltd.labs.pipeline;

import static datasoc.ltd.labs.pipeline.PageParser.GLOSSARY_TEXT_KEY;
import static datasoc.ltd.labs.pipeline.PageParser.TERM_NAME_KEY;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import datasoc.ltd.labs.config.TemplateConfig;
import java.net.URL;
import java.util.List;
import java.util.Map;
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
    int expectedSize = 2;
    String expectedTermName = "authentic data container";
    String expectedGlossaryText = "A mechanism for conveying data that allows the authenticity of its content to be proved.";
    Map<String, String> elements = pageParser.getSubpageElements(adcPage,
        acdcConfig.subpageConfig());
    assertThat(elements).hasSize(expectedSize);
    assertThat(elements).containsOnlyKeys(TERM_NAME_KEY, GLOSSARY_TEXT_KEY);
    assertThat(elements.get(TERM_NAME_KEY)).isEqualTo(expectedTermName);
    assertThat(elements.get(GLOSSARY_TEXT_KEY)).isEqualTo(expectedGlossaryText);
  }

}