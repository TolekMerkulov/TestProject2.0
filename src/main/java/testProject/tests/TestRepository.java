package testProject.tests;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestRepository {

    private final ServletContext ctx;
    private final ObjectMapper mapper = new ObjectMapper();

    public TestRepository(ServletContext ctx) {this.ctx = ctx;}


    public List<Test> findAllTests() throws IOException {
        List<Test> tests = new ArrayList<>();
        String realPath = ctx.getRealPath("/WEB-INF/data/tests");
        File dir = new File(realPath);
        if (!dir.exists() && !dir.isDirectory()) {
            throw new IOException("Tests directory does not exist");
        }

        File[] files = dir.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.getName().endsWith(".json")) {
                    tests.add(mapper.readValue(f, Test.class));
                }
            }
        }
        return tests;
    }
}
