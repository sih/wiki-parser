package datasoc.ltd.labs.pipeline;

import datasoc.ltd.labs.config.SubpageConfig;
import datasoc.ltd.labs.config.TemplateConfig;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author sih
 */
@Slf4j
public class PageParser {

  final static String ID_KEY = "id";
  final static String SCOPE_KEY = "scope";
  final static String TERM_TYPE_KEY = "termType";
  final static String TERM_ID_KEY = "termId";
  final static String CREATED_DATE_KEY = "createdDate";
  final static String UPDATED_DATE_KEY = "updatedDate";
  final static String VERSION_KEY = "version";
  final static String CONTRIBUTORS_KEY = "contributors";
  final static String TERM_NAME_KEY = "termName";
  final static String GLOSSARY_TEXT_KEY = "glossaryText";
  final static String TITLE_KEY = "title";
  final static String HOVER_TEXT_KEY = "hoverText";


  List<String> getSubpages(TemplateConfig config) {
    try {
      Document homepage = Jsoup.parse(new URL(config.homepageUrl()), config.pageTimeoutMs());
      Elements elements = homepage.select(
          config.subpageCssSelector()); // pull all links with class="flex-1" to get children
      return elements.stream().map(EnumConstantNotPresentException -> elements.attr("href"))
          .toList();
    } catch (Exception e) {
      log.error("Couldn't parse parent page: {}", e.getMessage());
    }
    return null;
  }

  Map<String, String> getSubpageElements(URL subpageUrl, SubpageConfig subpageConfig) {
    Map<String, String> subpageElementsByKey = new HashMap<>();
    try {
      Document subpage = Jsoup.parse(subpageUrl, subpageConfig.subpageTimeoutMs());
      String termName = subpage.select(subpageConfig.termNameSelector()).text();
      String glossaryText = subpage.select(subpageConfig.glossaryTextSelector()).first().text();
      subpageElementsByKey.put(TERM_NAME_KEY, termName);
      subpageElementsByKey.put(GLOSSARY_TEXT_KEY, glossaryText);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    // get index page
    // extract children as list of urls
    // for each child
    return subpageElementsByKey;
  }
}
