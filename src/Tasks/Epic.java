package Tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    public ArrayList<Integer> subtasksIds;

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksIds, epic.subtasksIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksIds);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasksIds='" + subtasksIds + '\'' +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskId=" + taskId +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
