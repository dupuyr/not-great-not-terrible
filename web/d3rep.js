var title = "Top Contributions";
  data = [{Month: "31/08/2020", Code: 69},
         {Month: "31/07/2020", Code: 167}]
;

d3.select("body")
  .append("h3")
  .text(title);

d3.select("body")
  .selectAll("div")
  .data(data)
  .enter()
  .append("div")
  .style("width", function(d) { return d.amount * 40 + "px"; })
  .style("height", "15px");
});