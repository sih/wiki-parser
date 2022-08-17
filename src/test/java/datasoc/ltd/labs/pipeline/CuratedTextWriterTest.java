package datasoc.ltd.labs.pipeline;

import static datasoc.ltd.labs.pipeline.CuratedTextWriter.TERMS_DIR;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author sih
 */
class CuratedTextWriterTest {

  private PageData acdcPageData;
  private CuratedTextWriter writer;

  @BeforeEach
  void setUp() throws Exception {
    Files.walk(Paths.get(TERMS_DIR))
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete);
    acdcPageData = new PageData(
        "acdc",
        "authentic-data-container",
        "authentic-data-container",
        "authentic data container",
        "A mechanism for conveying data that allows the authenticity of its content to be proved.");
  }

  @Test
  void testCreateTermFile() throws Exception {
    writer = new CuratedTextWriter(WikiTemplateType.acdc);
    String expectedStartOfContent = "# \nid: authentic-data-container";
    Path termsDir = Path.of(TERMS_DIR);
    Path expectedFilePath = Paths.get(TERMS_DIR, "authentic-data-container.md");
    assertThat(Files.exists(termsDir)).isFalse();
    writer.substituteAndWrite(acdcPageData);
    assertThat(Files.exists(termsDir)).isTrue();          // should create directory
    assertThat(Files.exists(expectedFilePath)).isTrue();  // should create file
    String fileContent = new String(Files.readAllBytes(expectedFilePath));
    assertThat(fileContent).startsWith(expectedStartOfContent);
  }

}