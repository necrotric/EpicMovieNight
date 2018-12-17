/*var app = new Vue({
    el: '#root',
    data:{
        movies:[],
        search: '',
        mainSearch: '',
        movieSearch: [],
        showSearchList : true,
        showmovie : false
    },
    methods:{
        fetchPosts: function(){
            this.showSearchList=true;
            this.showmovie=false
            axios.get("http://localhost:8080/title/?title=" + this.mainSearch).then(function(response){
                this.movies = response.data;
                console.log(mainSearch);
                console.log(this.mainSearch);


            }.bind(this))
        },
        movieInfo: function(){
            this.showSearchList=false;
            this.showmovie=true
            axios.get("http://localhost:8080/title/movie/?imdb=" + this.search).then(function(response){
                this.movieSearch = response.data;
                this.mainSearch = movieSearch.Title;
            }.bind(this))
        }
    }
})*/