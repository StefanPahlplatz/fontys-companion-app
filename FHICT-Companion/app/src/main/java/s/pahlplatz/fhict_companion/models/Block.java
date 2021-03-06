package s.pahlplatz.fhict_companion.models;

/**
 * Created by Stefan on 22-12-2016.
 * <p>
 * Class that represents a block in the schedule.
 */

public class Block implements java.io.Serializable {
    private static final int START_OF_TIME = 11;
    private static final int END_OF_TIME = 16;

    private final String subject;
    private final String teacherAbbr;
    private String start;
    private String end;
    private String room;

    /**
     * Default constructor.
     */
    public Block(final String room, final String subject, final String teacherAbbr,
                 final String start, final String end) {
        this.room = room;
        this.subject = subject;
        this.teacherAbbr = teacherAbbr;

        try {
            this.start = start.substring(START_OF_TIME, END_OF_TIME);
            this.end = end.substring(START_OF_TIME, END_OF_TIME);
        } catch (Exception e) {
            // Do nothing.
        }
    }

    /**
     * Constructor for breaks.
     *
     * @param start full datetime string.
     * @param end   full datetime string.
     */
    Block(final String start, final String end) {
        this.room = "";
        this.subject = "Break";
        this.teacherAbbr = "";
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the end time of the block.
     *
     * @return String in hh:mm format.
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end time of the block.
     *
     * @param end String in hh:mm format.
     */
    void setEnd(final String end) {
        this.end = end;
    }

    /**
     * Returns the start time of the block.
     *
     * @return String in hh:mm format.
     */
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Returns the abbreviation of the teacher.
     */
    public String getTeacherAbbr() {
        return teacherAbbr;
    }

    /**
     * Returns the subject of the block.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns the room of the block.
     */
    public String getRoom() {
        return room;
    }

    /**
     * Sets the room of the block.
     *
     * @param room string.
     */
    void setRoom(String room) {
        this.room = room;
    }

    /**
     * Returns true or false depending on if all of the properties are the same.
     * @param other to check.
     * @return same or not.
     */
    public boolean equals(Block other) {
        return this.subject.equals(other.subject) &&
                this.teacherAbbr.equals(other.teacherAbbr) &&
                this.start.equals(other.start) &&
                this.end.equals(other.end) &&
                this.room.equals(other.room);
    }

    @Override
    public String toString() {
        if (subject.equals("Break")) {
            return "Break";
        } else {
            return String.format("Subject: %-10s\tRoom: %-10s\tTeacher: %-10s\tStart: %-10s\tEnd: %-10s",
                    subject, room, teacherAbbr, start, end);
        }
    }
}
