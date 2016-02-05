$(function() {
	$("#login_btn").click(function() {
		$("#mainForm").css("display", "none");
		$("#mainLogin").css("display", "inline")
	});
});
// star rating
$(function() {
	var starRating = function() {
		var $star = $(".star-input"), $result = $star.find("output>b");
		$(document).on("focusin", ".star-input>.input", function() {
			$(this).addClass("focus");
		}).on("focusout", ".star-input>.input", function() {
			var $this = $(this);
			setTimeout(function() {
				if ($this.find(":focus").length === 0) {
					$this.removeClass("focus");
				}
			}, 100);
		}).on("change", ".star-input :radio", function() {
			$result.text($(this).next().text());
		}).on("mouseover", ".star-input label", function() {
			$result.text($(this).text());
		}).on("mouseleave", ".star-input>.input", function() {
			var $checked = $star.find(":checked");
			if ($checked.length === 0) {
				$result.text("0");
			} else {
				$result.text($checked.next().text());
			}
		});
	};
	starRating();
});
$(function() {
	var starRating1 = function() {
		var $star1 = $(".star-input1"), $result1 = $star1.find("output>b");
		$(document).on("focusin", ".star-input1>.input1", function() {
			$(this).addClass("focus");
		}).on("focusout", ".star-input1>.input1", function() {
			var $this1 = $(this);
			setTimeout(function() {
				if ($this1.find(":focus").length === 0) {
					$this1.removeClass("focus");
				}
			}, 100);
		}).on("change", ".star-input1 :radio", function() {
			$result1.text($(this).next().text());
		}).on("mouseover", ".star-input1 label", function() {
			$result1.text($(this).text());
		}).on("mouseleave", ".star-inpu1t1>.input1", function() {
			var $checked1 = $star1.find(":checked");
			if ($checked1.length === 0) {
				$result1.text("0");
			} else {
				$result1.text($checked1.next().text());
			}
		});
	};
	starRating1();
});
$(function() {
	var starRating2 = function() {
		var $star2 = $(".star-input2"), $result2 = $star2.find("output>b");
		$(document).on("focusin", ".star-input2>.input2", function() {
			$(this).addClass("focus");
		}).on("focusout", ".star-input2>.input2", function() {
			var $this2 = $(this);
			setTimeout(function() {
				if ($this2.find(":focus").length === 0) {
					$this2.removeClass("focus");
				}
			}, 100);
		}).on("change", ".star-input2 :radio", function() {
			$result2.text($(this).next().text());
		}).on("mouseover", ".star-input2 label", function() {
			$result2.text($(this).text());
		}).on("mouseleave", ".star-inpu1t2>.input2", function() {
			var $checked2 = $star2.find(":checked");
			if ($checked2.length === 0) {
				$result2.text("0");
			} else {
				$result2.text($checked2.next().text());
			}
		});
	};
	starRating2();
});