package datasoc.ltd.labs.pipeline;

import datasoc.ltd.labs.config.SubpageConfig;
import datasoc.ltd.labs.config.TemplateConfig;
import java.net.URL;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

  PageData getSubpageElements(URL subpageUrl, TemplateConfig templateConfig) {
    PageData pageData = null;
    try {
      Document subpage = Jsoup.parse(subpageUrl, templateConfig.pageTimeoutMs());
      SubpageConfig subpageConfig = templateConfig.subpageConfig();
      String termName = subpage.select(subpageConfig.termNameSelector()).text();
      String id = StringUtils.replace(" ", termName, "-");
      String glossaryText = subpage.select(subpageConfig.glossaryTextSelector()).first().text();

      pageData = new PageData(templateConfig.scope(), id, id, termName, glossaryText);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return pageData;
  }
}
