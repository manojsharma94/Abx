package com.kot.mylibrary123.Model;

import java.util.List;

public class Problem_model {
    String name;
    List<Sub_problem>problem_list;
    boolean check=false;

    public Problem_model(String name, List<Sub_problem> problem_list) {
        this.name = name;
        this.problem_list = problem_list;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sub_problem> getProblem_list() {
        return problem_list;
    }

    public void setProblem_list(List<Sub_problem> problem_list) {
        this.problem_list = problem_list;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
