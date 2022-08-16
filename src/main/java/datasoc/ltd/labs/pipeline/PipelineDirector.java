package datasoc.ltd.labs.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import datasoc.ltd.labs.config.TemplateConfig;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sih
 */
@Slf4j
@RequiredArgsConstructor
public class PipelineDirector {

  private final PageParser pageParser;
  private static final ObjectMapper YAML_PARSER = new ObjectMapper(new YAMLFactory());


  public int convert(WikiTemplateType templateType) {
    int pagesConverted = 0;
    try {
      String selectorsYaml = String.join(".", templateType.toString(), "selectors", "yaml");
      InputStream yamlStream = this.getClass().getResourceAsStream(selectorsYaml);
      TemplateConfig templateConfig = YAML_PARSER.readValue(yamlStream, TemplateConfig.class);

    } catch (Exception e) {
      log.error("Could not convert {} as encountered config error: {}", templateType.toString(),
          e.getMessage());
    }

    return pagesConverted;
  }


}
