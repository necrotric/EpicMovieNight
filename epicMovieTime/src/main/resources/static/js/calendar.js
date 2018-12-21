window.onload = function(){
    let date = new Date();
    document.getElementById("currentYear").innerHTML = date.getFullYear();

    let months = ["January","February","March","April","May","June","July","August","September","October","November","December"];
    document.getElementById("currentMonth").innerHTML = months[date.getMonth()];

    let weekdays = document.getElementById("currentWeekday"),
        today = new Date().getDay()-1;
    if (today === -1)
        today = 6;
        weekdays.children[today].style.color = "red",weekdays.children[today].style.fontWeight = "bold",weekdays.children[today].style.fontSize = "20px";;

    let dates = document.getElementById("currentDate"),
        day = new Date().getDate()-1;
    if (day === -1)
        day = 31;
    dates.children[day].style.color = "red",dates.children[day].style.fontWeight = "bold",
        dates.children[day].style.fontSize = "20px";

    // let month = date.getMonth();
    // let year = date.getFullYear();
    // let first_date = months[month] + " " + 1 + " " + year;
    // let tmp = new Date(first_date).toDateString();
    // let first_day = tmp.substring(0, 3);
    // let day_name = ["Mon","Tue","Wed","Thu","Fri","Sat","Sun"];
    // let day_no = day_name.indexOf(first_day);
    // let days = new Date(year, month+1, 0).getDate();
    // let calendar = get_calendar(day_no, days);
    // document.getElementById("currentDate").appendChild(calendar);

};

// function get_calendar(day_no, days){
//     let table = document.createElement('ul');
//     let ul = document.createElement('ul');
//
//     let c;
//     for(c=0; c<=6; c++){
//         if(c === day_no){
//             break;
//         }
//         let li = document.createElement('li');
//         li.innerHTML = "";
//         ul.appendChild(li);
//     }
//
//     let count = 1;
//     for(; c<=5; c++){
//         let li = document.createElement('li');
//         li.innerHTML = count;
//         count++;
//         ul.appendChild(li);
//     }
//     table.appendChild(ul);
//
//     for(let r=2; r<7; r++){
//         for(let c=0; c<=6; c++){
//             if(count > days){
//                 table.appendChild(ul);
//                 return table;
//             }
//             let li = document.createElement('li');
//             li.innerHTML = count;
//             count++;
//             ul.appendChild(li);
//         }
//         table.appendChild(ul);
//     }
//     return table;
// }