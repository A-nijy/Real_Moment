package rm.backend.enumeration;

public enum ImageLocation {

    BRAND("로고"),
    MAIN_IMAGE("홍보"),
    BACK_GROUND("배경"),
    SALE("세일"),
    NEW_ITEM("신상")
    ;


    // 문자열을 저장할 필드
    private String imageLocation;

    // 생성자 (싱글톤)
    private ImageLocation(String imageLocation){
        this.imageLocation = imageLocation;
    }

    // Getter (해당 상수의 설명 문자열을 반환)
    public String getImageLocation() {
        return imageLocation;
    }

    // 상수 설명 문자열을 받아와서 상수 저장
    public static ImageLocation fromString(String text) {
        for (ImageLocation i : ImageLocation.values()) {
            if (i.getImageLocation().equalsIgnoreCase(text)) {
                return i;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

}
