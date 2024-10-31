package com.lunadev.worktime.Enum;

public enum WorkType {
        CHECK_IN,     // 출근
        CHECK_OUT,    // 퇴근
        OVERTIME,     // 추가근로
        VACATION,         // 휴가
        NOT_CHECK_IN;   // 미출근

        private static final WorkType[] list = WorkType.values();

        public static WorkType getValue(int i) {
                return list[i];
        }

        public static int listGetLastIndex() {
                return list.length - 1;
        }
}
