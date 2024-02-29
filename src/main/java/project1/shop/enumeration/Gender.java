package project1.shop.enumeration;

public enum Gender {

    MAN("남"),
    WOMAN("여")
    ;



    // 문자열을 저장할 필드
    private String gender;

    // 생성자 (싱글톤)
    private Gender(String gender){
        this.gender = gender;
    }

    // Getter (해당 상수의 설명 문자열을 반환)
    public String getGender() {
        return gender;
    }

    // 상수 설명 문자열을 받아와서 상수 저장
    public static Gender fromString(String text) {
        for (Gender g : Gender.values()) {
            if (g.getGender().equalsIgnoreCase(text)) {
                return g;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
