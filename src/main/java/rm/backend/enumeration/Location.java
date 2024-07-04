package rm.backend.enumeration;

public enum Location {

    HOME("집"),
    COMPANY("회사"),
    ECT("기타")
    ;

    // 문자열을 저장할 필드
    private String location;

    // 생성자 (싱글톤)
    private Location(String location){
        this.location = location;
    }

    // Getter (해당 상수의 설명 문자열을 반환)
    public String getLocation() {
        return location;
    }

    // 상수 설명 문자열을 받아와서 상수 저장
    public static Location fromString(String text) {
        for (Location l : Location.values()) {
            if (l.getLocation().equalsIgnoreCase(text)) {
                return l;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
