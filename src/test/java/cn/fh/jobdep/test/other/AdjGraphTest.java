package cn.fh.jobdep.test.other;

import cn.fh.jobdep.graph.*;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AdjGraphTest {
    @Test
    public void testBuildAdj() {
        List<JobEdge> edges = Arrays.asList(
                new JobEdge(
                        new JobVertex(0, "-", "-"),
                        new JobVertex(1, "-", "-")
                ),
                new JobEdge(
                        new JobVertex(0, "-", "-"),
                        new JobVertex(2, "-", "-")
                ),
                new JobEdge(
                        new JobVertex(1, "-", "-"),
                        new JobVertex(3, "-", "-")
                ),
                new JobEdge(
                        new JobVertex(2, "-", "-"),
                        new JobVertex(3, "-", "-")
                )
        );

        AdjTaskGraph graph = new AdjTaskGraph(edges);
        System.out.println(graph);

        System.out.println("the child of 0:" + graph.getChildren(0));
        System.out.println("the parents of 3:" + graph.getParents(3));
        System.out.println("the parents of 2:" + graph.getParents(2));
        System.out.println("the parents of 1:" + graph.getParents(1));
        System.out.println("the child of 3:" + graph.getChildren(3));

        System.out.println("the roots:" + graph.getRoots());
        System.out.println("the end:" + graph.getLasts());
        System.out.println(graph.hasCircle());
    }

    @Test
    public void testBuildComplexAdj() {
        List<JobEdge> edges = Arrays.asList(
                new JobEdge(
                        new JobVertex(0, "job0"),
                        new JobVertex(2, "job2")
                ),
                new JobEdge(
                        new JobVertex(0, "job0"),
                        new JobVertex(3, "job3")
                ),
                new JobEdge(
                        new JobVertex(1, "job1"),
                        new JobVertex(3, "job3")
                ),
                new JobEdge(
                        new JobVertex(2, "job2"),
                        new JobVertex(4, "job4")
                ),
                new JobEdge(
                        new JobVertex(3, "job3"),
                        new JobVertex(5, "job5")
                ),
                new JobEdge(
                        new JobVertex(4, "job4"),
                        new JobVertex(6, "job6")
                ),
                new JobEdge(
                        new JobVertex(5, "job5"),
                        new JobVertex(6, "job6")
                ),

                // orphan
                new JobEdge(
                        new JobVertex(7, "job7"),
                        new JobVertex(8, "job8")
                )
        );

        AdjTaskGraph graph = new AdjTaskGraph(edges);
        System.out.println(graph);

        System.out.println("the child of 0:" + graph.getChildren(0));
        System.out.println("the parents of 3:" + graph.getParents(3));
        System.out.println("the parents of 2:" + graph.getParents(2));
        System.out.println("the parents of 1:" + graph.getParents(1));
        System.out.println("the child of 3:" + graph.getChildren(3));

        System.out.println("the roots:" + graph.getRoots());
        System.out.println("the end:" + graph.getLasts());
        System.out.println(graph.format(new StringFormatter()));
        System.out.println(graph.format(new JobFormatter()));
        System.out.println(graph.hasCircle());

    }

    @Test
    public void testCycle() {
        List<JobEdge> edges = Arrays.asList(
                new JobEdge(
                        new JobVertex(0, "-", "-"),
                        new JobVertex(1, "-", "-")
                ),
                new JobEdge(
                        new JobVertex(1, "-", "-"),
                        new JobVertex(2, "-", "-")
                ),
                new JobEdge(
                        new JobVertex(2, "-", "-"),
                        new JobVertex(3, "-", "-")
                ),
                new JobEdge(
                        new JobVertex(3, "-", "-"),
                        new JobVertex(4, "-", "-")
                ),
                new JobEdge(
                        new JobVertex(4, "-", "-"),
                        new JobVertex(2, "-", "-")
                )
        );

        AdjTaskGraph graph = new AdjTaskGraph(edges);
        System.out.println(graph);
        System.out.println(graph.hasCircle());
    }

    @Test
    public void testLoadYaml() throws Exception {
        Yaml yaml = new Yaml();
        Map<String, Object> map = yaml.loadAs(new FileInputStream("src/test/resources/task.yaml"), Map.class);
        System.out.println(map);
    }
}
