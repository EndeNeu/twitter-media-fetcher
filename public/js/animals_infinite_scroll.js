(function($) {

  scroller = {

    page: 1,

    working: false,

    scrollListener: function() {
      $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() >= $(document).height() - 2500) {
          scroller.handler()
        }
      });
    },

    handler: function() {
      if(!scroller.working) {
        scroller.working = true;
        $.ajax({
          type: 'GET',
          async: true,
          url: "/" + scroller.page,
          dataType: 'text',
          success: function(data) {
            $(".ul-container").append(data)
            if(data) {
              scroller.page += 1;
              scroller.working = false;
            }
            else {
              $(window).unbind("scroll")
            }
          },
          // on failure.
          error: function(xhr, status, error) {
            // console.log(error);
            // console.log(xhr);
            // console.log(status);
          },
          complete: function(data) {
          }
        });
      }
    }
  }

  scroller.scrollListener()

})
(jQuery)