package datasoc.ltd.labs.pipeline;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sih
 */
@Slf4j
public class CuratedTextWriter {

  private static final String DEFAULT_DIRECTORY = "terms";
  private final WikiTemplateType templateType;
  private final Mustache mustache;
  static final String TERMS_DIR = "./terms";

  CuratedTextWriter(WikiTemplateType templateType) {
    this.templateType = templateType;
    String templateName = String.join(".", templateType.toString(), "mustache");
    MustacheFactory mf = new DefaultMustacheFactory();
    this.mustache = mf.compile(templateName);
  }


  public void substituteAndWrite(PageData data) {
    String termFileName = String.join(".", data.termId(), "md");
    Path termFilePath = Paths.get(TERMS_DIR, termFileName);
    try {
      Files.createDirectories(Paths.get(TERMS_DIR));
      FileWriter writer = new FileWriter(termFilePath.toFile());
      mustache.execute(writer, data).close();
    } catch (IOException ioe) {
      log.error("Could not write term file {}. Exception was {}", termFileName, ioe.getMessage());
    }
  }

}
