var App = {};

(function() {
  // Filter plugin
  $.fn.filter = function() {
    var target = $(this).data("target");
    var $target = $(target);

    $(this)
      .find("a")
      .on("click", function(e) {
        e.preventDefault();
        var filter = $(this).attr("href");
        $(this)
          .closest("ul")
          .find("li")
          .removeClass("is-active");
        $(this)
          .parent()
          .addClass("is-active");

        if (filter == "all") {
          $target
            .find("[data-filter]")
            .removeClass("hidden")
            .addClass("show");
        } else {
          $target
            .find(`[data-filter="${filter}"]`)
            .removeClass("hidden")
            .addClass("show");
          $target
            .find(`[data-filter!="${filter}"]`)
            .removeClass("show")
            .addClass("hidden");
        }
      });

    $(this)
      .find(".is-active a")
      .click();
  };

  App = {
    components: {
      filter: function(target) {}
    },
    setupListeners: function() {},
    init: function() {
      $(".tab-filter").filter();
      $("#qa-threads .tabs").filter();
    }
  };

  App.init();
})();
