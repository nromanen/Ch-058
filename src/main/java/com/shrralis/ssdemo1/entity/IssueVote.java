/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.entity;

import com.shrralis.ssdemo1.entity.interfaces.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import static com.shrralis.ssdemo1.entity.IssueVote.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class IssueVote implements Identifiable<IssueVote.Id> {
    public static final String TABLE_NAME = "issues_votes";
    public static final String ISSUE_COLUMN_NAME = "issue_id";
    public static final String VOTER_COLUMN_NAME = "voter_id";
    public static final String VOTE_COLUMN_NAME = "vote";

    private Id id;
    private Issue issue;
    private User voter;
    private Boolean vote = true;

    @EmbeddedId
    @Override
    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    @ManyToOne
    @MapsId("issueId")
    @JoinColumn(name = ISSUE_COLUMN_NAME)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @ManyToOne
    @MapsId("voterId")
    @JoinColumn(name = VOTER_COLUMN_NAME)
    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    @NotNull
    @Column(name = VOTE_COLUMN_NAME, nullable = false)
    public Boolean getVote() {
        return vote;
    }

    public void setVote(Boolean vote) {
        this.vote = vote;
    }

    @Embeddable
    public static class Id implements Serializable {
        private Integer issueId;
        private Integer voterId;

        private Id() {
        }

        public Id(Integer issueId, Integer voterId) {
            this.issueId = issueId;
            this.voterId = voterId;
        }

        @NotNull
        @Column(name = ISSUE_COLUMN_NAME, nullable = false)
        public Integer getIssueId() {
            return issueId;
        }

        public void setIssueId(Integer issueId) {
            this.issueId = issueId;
        }

        @NotNull
        @Column(name = VOTER_COLUMN_NAME, nullable = false)
        public Integer getVoterId() {
            return voterId;
        }

        public void setVoterId(Integer voterId) {
            this.voterId = voterId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Id id = (Id) o;

            return Objects.equals(issueId, id.issueId) &&
                    Objects.equals(voterId, id.voterId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(issueId, voterId);
        }
    }
}
