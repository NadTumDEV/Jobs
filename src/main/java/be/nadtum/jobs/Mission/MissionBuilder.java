package be.nadtum.jobs.Mission;

import be.nadtum.jobs.Builder.RewardBuilder;
import be.nadtum.jobs.Builder.StainBuilder;

import java.util.List;

public class MissionBuilder {

    private String jobName;
    private List<StainBuilder> stainList;
    private List<RewardBuilder> rewardList;

    public MissionBuilder(String jobName, List<StainBuilder> stainList, List<RewardBuilder> rewardList) {
        this.jobName = jobName;
        this.stainList = stainList;
        this.rewardList = rewardList;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<StainBuilder> getStainList() {
        return stainList;
    }

    public void setStainList(List<StainBuilder> stainList) {
        this.stainList = stainList;
    }

    public List<RewardBuilder> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<RewardBuilder> rewardList) {
        this.rewardList = rewardList;
    }


}
