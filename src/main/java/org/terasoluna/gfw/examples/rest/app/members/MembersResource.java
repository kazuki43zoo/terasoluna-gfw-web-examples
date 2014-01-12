package org.terasoluna.gfw.examples.rest.app.members;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MembersResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<MemberResource> members = new ArrayList<>();

    private long totalCount;

    public List<MemberResource> getMembers() {
        return members;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void addMember(MemberResource member) {
        members.add(member);
    }

}
