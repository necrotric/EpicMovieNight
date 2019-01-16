let app = new Vue({
    el: '.movieIndex',
    data:{
        movies:[],
        search: '',
        mainSearch: '',
        bookMovie: '',
        startDate: '',
        endDate: '',
        oneMoreEnd:'',
        oneMoreStart:'',
        bookingMessage:'',
        movieSearch: [],
        showSearchList : true,
        showmovie: false,
        showBook: false,
        mainMovieTitle: false,
        bookResponse: [],
        calendarRespone: []
    },
    methods:{
        fetchPosts: function(){
            this.showBook=false;
            this.showSearchList=true;
            this.showmovie=false;
            axios.get("http://localhost:8080/title/?title=" + this.mainSearch).then(function(response){
                this.movies = response.data;
                console.log(mainSearch);
                console.log(this.mainSearch);
            }.bind(this))
        },
        movieInfo: function(){
            this.showBook=true;
            this.showSearchList=false;
            this.showmovie=true;
            axios.get("http://localhost:8080/title/movie/?imdb=" + this.search).then(function(response){
                this.movieSearch = response.data;
                this.mainSearch = movieSearch.Title;
            }.bind(this))
        },
        bookInfo: function(){
            console.log("book info");
            axios.get("http://localhost:8080/main.html/giveinfo").then(function(response){
                this.bookResponse = response.data;
            }.bind(this))
        },
        bookCalendar: function(){
            axios.get("http://localhost:8080/calendar/book/?startDate=" +this.startDate+ ":00&endDate=" +this.endDate+ ":00&summary="+this.bookMovie).then(function(response){
                this.bookingMessage = response.data
            }.bind(this))
        },
        calendarSuggestion: function() {
            axios.get("http://localhost:8080/main.html/suggestedDates").then(function (response) {
                this.calendarRespone = response.data
            }.bind(this))
        }
    }
});