const _daysInMonths = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
const _weekdayLabels = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday','Sunday'];
const _monthLabels = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
const _today = new Date();
const _todayComps = {
    year: _today.getFullYear(),
    month: _today.getMonth() + 1,
    day: _today.getDate(),
};

Vue.component('calendar', {
    template: '#calendar',
    data() {
        return {
            month: _todayComps.month,
            year: _todayComps.year,
        };
    },
    props: {
        dayKey: { type: String, default: 'label' },
    },
    computed: {
        monthIndex() {
            return this.month - 1;
        },
        isLeapYear() {
            return (this.year % 4 === 0 && this.year % 100 !== 0) || this.year % 400 === 0;
        },
        previousMonthComps() {
            if (this.month === 1) return {
                days: _daysInMonths[11],
                month: 12,
                year: this.year - 1,
            };
            return {
                days: (this.month === 3 && this.isLeapYear) ? 29 : _daysInMonths[this.month - 2],
                month: this.month - 1,
                year: this.year,
            };
        },
        nextMonthComps() {
            if (this.month === 12) return {
                days: _daysInMonths[0],
                month: 1,
                year: this.year + 1,
            };
            return {
                days: (this.month === 2 && this.isLeapYear) ? 29 : _daysInMonths[this.month],
                month: this.month + 1,
                year: this.year,
            };
        },
        months() {
            return _monthLabels.map((ml, i) => ({
                label: ml,
                label_1: ml.substring(0, 1),
                label_2: ml.substring(0, 2),
                label_3: ml.substring(0, 3),
                number: i + 1,
            }));
        },
        weekdays() {
            return _weekdayLabels.map((wl, i) => ({
                label: wl,
                label_1: wl.substring(0, 1),
                label_2: wl.substring(0, 2),
                label_3: wl.substring(0, 9),
                number: i + 1,
            }));
        },
        header() {
            const month = this.months[this.monthIndex];
            return {
                month: month,
                year: this.year.toString(),
                shortYear: this.year.toString().substring(2, 4),
                label: month.label + '\n ' + '       ' + this.year,
            };
        },
        firstWeekdayInMonth() {
            return new Date(this.year, this.monthIndex, 1).getDay();
        },
        daysInMonth() {
            if (this.month === 2 && this.isLeapYear) return 29;
            return _daysInMonths[this.monthIndex];
        },
        weeks() {
            const weeks = [];
            let previousMonth = true, thisMonth = false, nextMonth = false;
            let day = this.previousMonthComps.days - this.firstWeekdayInMonth + 2;
            let month = this.previousMonthComps.month;
            let year = this.previousMonthComps.year;
            for (let w = 1; w <= 6 && !nextMonth; w++) {
                const week = [];
                for (let d = 1; d <= 7; d++) {

                    if (previousMonth && d >= this.firstWeekdayInMonth) {
                        day = 1;
                        month = this.month;
                        year = this.year;
                        previousMonth = false;
                        thisMonth = true;
                    }
                    week.push ({
                        label: (day && thisMonth) ? day.toString() : '',
                        day,
                        weekday: d,
                        week: w,
                        month,
                        year,
                        date: new Date(year, month - 1, day),
                        beforeMonth: previousMonth,
                        afterMonth: nextMonth,
                        inMonth: thisMonth,
                        isToday: day === _todayComps.day && month === _todayComps.month && year === _todayComps.year,
                        isFirstDay: thisMonth && day === 1,
                        isLastDay: thisMonth && day === this.daysInMonth,
                    });

                    if (thisMonth && day >= this.daysInMonth) {
                        thisMonth = false;
                        nextMonth = true;
                        day = 1;
                        month = this.nextMonthComps.month;
                        year = this.nextMonthComps.year;
                    } else {
                        day++;
                    }
                }
                weeks.push(week);
            }
            return weeks;
        },
    },
    methods: {
        moveThisMonth() {
            this.month = _todayComps.month;
            this.year = _todayComps.year;
        },
        moveNextMonth() {
            const { month, year } = this.nextMonthComps;
            this.month = month;
            this.year = year;
        },
        movePreviousMonth() {
            const { month, year } = this.previousMonthComps;
            this.month = month;
            this.year = year;
        },
        moveNextYear() {
            this.year++;
        },
        movePreviousYear() {
            this.year--;
        },
    },
});

new Vue({
    el: '#eventCal',
    data: {
        showCustomUI: false,
        dateSelection: null,
    },
});