package datasoc.ltd.labs.pipeline;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * @author sih
 */
@RequiredArgsConstructor
public class CuratedTextWriter {

  private static final String DEFAULT_DIRECTORY = "terms";
  private final WikiTemplateType templateType;

  public void substituteAndWrite(Map<String, String> subs) {
    String templateName = String.join(".", templateType.toString(), "mustache");
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache m = mf.compile(templateName);

  }

}
