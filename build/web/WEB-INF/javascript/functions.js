function goToTopic(t){
    $.ajax({
      type: "POST",
      url: "/TopicServlet",
      data: { topic : t }
    });
}