! function(t, e) {
    "object" == typeof exports && "object" == typeof module ? module.exports = e() : "function" == typeof define && define.amd ? define("Litepicker", [], e) : "object" == typeof exports ? exports.Litepicker = e() : t.Litepicker = e()
}(window, (function() {
    return function(t) {
        var e = {};

        function i(o) {
            if (e[o]) return e[o].exports;
            var n = e[o] = {
                i: o,
                l: !1,
                exports: {}
            };
            return t[o].call(n.exports, n, n.exports, i), n.l = !0, n.exports
        }
        return i.m = t, i.c = e, i.d = function(t, e, o) {
            i.o(t, e) || Object.defineProperty(t, e, {
                enumerable: !0,
                get: o
            })
        }, i.r = function(t) {
            "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(t, Symbol.toStringTag, {
                value: "Module"
            }), Object.defineProperty(t, "__esModule", {
                value: !0
            })
        }, i.t = function(t, e) {
            if (1 & e && (t = i(t)), 8 & e) return t;
            if (4 & e && "object" == typeof t && t && t.__esModule) return t;
            var o = Object.create(null);
            if (i.r(o), Object.defineProperty(o, "default", {
                    enumerable: !0,
                    value: t
                }), 2 & e && "string" != typeof t)
                for (var n in t) i.d(o, n, function(e) {
                    return t[e]
                }.bind(null, n));
            return o
        }, i.n = function(t) {
            var e = t && t.__esModule ? function() {
                return t.default
            } : function() {
                return t
            };
            return i.d(e, "a", e), e
        }, i.o = function(t, e) {
            return Object.prototype.hasOwnProperty.call(t, e)
        }, i.p = "", i(i.s = 3)
    }([function(t, e, i) {
        "use strict";
        Object.defineProperty(e, "__esModule", {
            value: !0
        });
        class o extends Date {
            constructor(t = null, e = null, i = "en-US") {
                e ? super(o.parseDateTime(t, e, i)) : t ? super(o.parseDateTime(t)) : super(), this.lang = i
            }
            static parseDateTime(t, e = "YYYY-MM-DD", i = "en-US") {
                if (!t) return new Date(NaN);
                if (t instanceof Date) return new Date(t);
                if (/^\d{10,}$/.test(t)) return new Date(Number(t));
                if ("string" == typeof t) {
                    const o = e.match(/\[([^\]]+)]|Y{2,4}|M{1,4}|D{1,2}|d{1,4}/g);
                    if (o) {
                        const e = {
                            year: 1,
                            month: 2,
                            day: 3,
                            value: ""
                        };
                        let n = null,
                            s = null;
                        o.includes("MMM") && (n = [...Array(12).keys()].map(t => new Date(2019, t).toLocaleString(i, {
                            month: "short"
                        }))), o.includes("MMMM") && (s = [...Array(12).keys()].map(t => new Date(2019, t).toLocaleString(i, {
                            month: "long"
                        })));
                        for (const [t, i] of Object.entries(o)) {
                            const o = Number(t),
                                a = String(i);
                            switch (o > 0 && (e.value += ".*?"), a) {
                                case "YY":
                                case "YYYY":
                                    e.year = o + 1, e.value += `(\\d{${a.length}})`;
                                    break;
                                case "M":
                                    e.month = o + 1, e.value += "(\\d{1,2})";
                                    break;
                                case "MM":
                                    e.month = o + 1, e.value += `(\\d{${a.length}})`;
                                    break;
                                case "MMM":
                                    e.month = o + 1, e.value += `(${n.join("|")})`;
                                    break;
                                case "MMMM":
                                    e.month = o + 1, e.value += `(${s.join("|")})`;
                                    break;
                                case "D":
                                    e.day = o + 1, e.value += "(\\d{1,2})";
                                    break;
                                case "DD":
                                    e.day = o + 1, e.value += `(\\d{${a.length}})`
                            }
                        }
                        const a = new RegExp(`^${e.value}$`);
                        if (a.test(t)) {
                            const i = a.exec(t),
                                o = Number(i[e.year]);
                            let r = Number(i[e.month]) - 1;
                            n ? r = n.indexOf(i[e.month]) : s && (r = s.indexOf(i[e.month]));
                            const l = Number(i[e.day]) || 1;
                            return new Date(o, r, l)
                        }
                    }
                }
                return new Date(t)
            }
            static convertArray(t, e) {
                return t.map(t => t instanceof Array ? t.map(t => new o(t, e)) : new o(t, e))
            }
            getWeek(t) {
                const e = new Date(this.getTime()),
                    i = (this.getDay() + (7 - t)) % 7;
                e.setDate(e.getDate() - i);
                const o = e.getTime();
                return e.setMonth(0, 1), e.getDay() !== t && e.setMonth(0, 1 + (4 - e.getDay() + 7) % 7), 1 + Math.ceil((o - e.getTime()) / 6048e5)
            }
            clone() {
                return new o(this.getTime())
            }
            isBetween(t, e, i = "()") {
                switch (i) {
                    default:
                    case "()":
                        return this.getTime() > t.getTime() && this.getTime() < e.getTime();
                    case "[)":
                        return this.getTime() >= t.getTime() && this.getTime() < e.getTime();
                    case "(]":
                        return this.getTime() > t.getTime() && this.getTime() <= e.getTime();
                    case "[]":
                        return this.getTime() >= t.getTime() && this.getTime() <= e.getTime()
                }
            }
            isBefore(t, e = "seconds") {
                switch (e) {
                    case "second":
                    case "seconds":
                        return t.getTime() > this.getTime();
                    case "day":
                    case "days":
                        return new Date(t.getFullYear(), t.getMonth(), t.getDate()).getTime() > new Date(this.getFullYear(), this.getMonth(), this.getDate()).getTime();
                    case "month":
                    case "months":
                        return new Date(t.getFullYear(), t.getMonth(), 1).getTime() > new Date(this.getFullYear(), this.getMonth(), 1).getTime()
                }
                throw new Error("isBefore: Invalid unit!")
            }
            isSameOrBefore(t, e = "seconds") {
                switch (e) {
                    case "second":
                    case "seconds":
                        return t.getTime() >= this.getTime();
                    case "day":
                    case "days":
                        return new Date(t.getFullYear(), t.getMonth(), t.getDate()).getTime() >= new Date(this.getFullYear(), this.getMonth(), this.getDate()).getTime();
                    case "month":
                    case "months":
                        return new Date(t.getFullYear(), t.getMonth(), 1).getTime() >= new Date(this.getFullYear(), this.getMonth(), 1).getTime()
                }
                throw new Error("isSameOrBefore: Invalid unit!")
            }
            isAfter(t, e = "seconds") {
                switch (e) {
                    case "second":
                    case "seconds":
                        return this.getTime() > t.getTime();
                    case "day":
                    case "days":
                        return new Date(this.getFullYear(), this.getMonth(), this.getDate()).getTime() > new Date(t.getFullYear(), t.getMonth(), t.getDate()).getTime();
                    case "month":
                    case "months":
                        return new Date(this.getFullYear(), this.getMonth(), 1).getTime() > new Date(t.getFullYear(), t.getMonth(), 1).getTime()
                }
                throw new Error("isAfter: Invalid unit!")
            }
            isSameOrAfter(t, e = "seconds") {
                switch (e) {
                    case "second":
                    case "seconds":
                        return this.getTime() >= t.getTime();
                    case "day":
                    case "days":
                        return new Date(this.getFullYear(), this.getMonth(), this.getDate()).getTime() >= new Date(t.getFullYear(), t.getMonth(), t.getDate()).getTime();
                    case "month":
                    case "months":
                        return new Date(this.getFullYear(), this.getMonth(), 1).getTime() >= new Date(t.getFullYear(), t.getMonth(), 1).getTime()
                }
                throw new Error("isSameOrAfter: Invalid unit!")
            }
            isSame(t, e = "seconds") {
                switch (e) {
                    case "second":
                    case "seconds":
                        return this.getTime() === t.getTime();
                    case "day":
                    case "days":
                        return new Date(this.getFullYear(), this.getMonth(), this.getDate()).getTime() === new Date(t.getFullYear(), t.getMonth(), t.getDate()).getTime();
                    case "month":
                    case "months":
                        return new Date(this.getFullYear(), this.getMonth(), 1).getTime() === new Date(t.getFullYear(), t.getMonth(), 1).getTime()
                }
                throw new Error("isSame: Invalid unit!")
            }
            add(t, e = "seconds") {
                switch (e) {
                    case "second":
                    case "seconds":
                        this.setSeconds(this.getSeconds() + t);
                        break;
                    case "day":
                    case "days":
                        this.setDate(this.getDate() + t);
                        break;
                    case "month":
                    case "months":
                        this.setMonth(this.getMonth() + t)
                }
                return this
            }
            subtract(t, e = "seconds") {
                switch (e) {
                    case "second":
                    case "seconds":
                        this.setSeconds(this.getSeconds() - t);
                        break;
                    case "day":
                    case "days":
                        this.setDate(this.getDate() - t);
                        break;
                    case "month":
                    case "months":
                        this.setMonth(this.getMonth() - t)
                }
                return this
            }
            diff(t, e = "seconds") {
                switch (e) {
                    default:
                    case "second":
                    case "seconds":
                        return this.getTime() - t.getTime();
                    case "day":
                    case "days":
                        return Math.round((this.getTime() - t.getTime()) / 864e5);
                    case "month":
                    case "months":
                }
            }
            format(t, e = "en-US") {
                let i = "";
                const o = t.match(/\[([^\]]+)]|Y{2,4}|M{1,4}|D{1,2}|d{1,4}/g);
                if (o) {
                    let n = null,
                        s = null;
                    o.includes("MMM") && (n = [...Array(12).keys()].map(t => new Date(2019, t).toLocaleString(e, {
                        month: "short"
                    }))), o.includes("MMMM") && (s = [...Array(12).keys()].map(t => new Date(2019, t).toLocaleString(e, {
                        month: "long"
                    })));
                    for (const [e, a] of Object.entries(o)) {
                        const r = Number(e),
                            l = String(a);
                        if (r > 0) {
                            const e = o[r - 1];
                            i += t.substring(t.indexOf(e) + e.length, t.indexOf(l))
                        }
                        switch (l) {
                            case "YY":
                                i += String(this.getFullYear()).slice(-2);
                                break;
                            case "YYYY":
                                i += String(this.getFullYear());
                                break;
                            case "M":
                                i += String(this.getMonth() + 1);
                                break;
                            case "MM":
                                i += `0${this.getMonth()+1}`.slice(-2);
                                break;
                            case "MMM":
                                i += n[this.getMonth()];
                                break;
                            case "MMMM":
                                i += s[this.getMonth()];
                                break;
                            case "D":
                                i += String(this.getDate());
                                break;
                            case "DD":
                                i += `0${this.getDate()}`.slice(-2)
                        }
                    }
                }
                return i
            }
        }
        e.DateTime = o
    }, function(t, e, i) {
        var o = i(5);
        "string" == typeof o && (o = [
            [t.i, o, ""]
        ]);
        var n = {
            insert: "head",
            singleton: !1
        };
        i(7)(o, n);
        o.locals && (t.exports = o.locals)
    }, function(t, e, i) {
        "use strict";
        Object.defineProperty(e, "__esModule", {
            value: !0
        });
        const o = i(4),
            n = i(0),
            s = i(1);
        class a extends o.Calendar {
            constructor(t) {
                super(), this.options = Object.assign(Object.assign({}, this.options), t), (this.options.allowRepick && this.options.inlineMode || !this.options.elementEnd) && (this.options.allowRepick = !1), this.options.lockDays.length && (this.options.lockDays = n.DateTime.convertArray(this.options.lockDays, this.options.lockDaysFormat)), this.options.bookedDays.length && (this.options.bookedDays = n.DateTime.convertArray(this.options.bookedDays, this.options.bookedDaysFormat));
                let [e, i] = this.parseInput();
                this.options.startDate && (this.options.singleMode || this.options.endDate) && (e = new n.DateTime(this.options.startDate, this.options.format, this.options.lang)), e && this.options.endDate && (i = new n.DateTime(this.options.endDate, this.options.format, this.options.lang)), e instanceof Date && !isNaN(e.getTime()) && (this.options.startDate = new n.DateTime(e, this.options.format, this.options.lang)), this.options.startDate && i instanceof Date && !isNaN(i.getTime()) && (this.options.endDate = new n.DateTime(i, this.options.format, this.options.lang)), !this.options.singleMode || this.options.startDate instanceof Date || (this.options.startDate = null), this.options.singleMode || this.options.startDate instanceof Date && this.options.endDate instanceof Date || (this.options.startDate = null, this.options.endDate = null);
                for (let t = 0; t < this.options.numberOfMonths; t += 1) {
                    const e = this.options.startDate instanceof Date ? this.options.startDate.clone() : new n.DateTime;
                    e.setMonth(e.getMonth() + t), this.calendars[t] = e
                }
                this.onInit()
            }
            onInit() {
            	

                document.addEventListener("click", t => this.onClick(t), !0), this.picker = document.createElement("div"), this.picker.className = s.litepicker, this.picker.style.display = "none", this.picker.addEventListener("keydown", t => this.onKeyDown(t), !0), this.picker.addEventListener("mouseenter", t => this.onMouseEnter(t), !0), this.picker.addEventListener("mouseleave", t => this.onMouseLeave(t), !1), this.options.element instanceof HTMLElement && this.options.element.addEventListener("change", t => this.onInput(t), !0), this.options.elementEnd instanceof HTMLElement && this.options.elementEnd.addEventListener("change", t => this.onInput(t), !0), this.render(), this.options.parentEl ? this.options.parentEl instanceof HTMLElement ? this.options.parentEl.appendChild(this.picker) : document.querySelector(this.options.parentEl).appendChild(this.picker) : this.options.inlineMode ? this.options.element instanceof HTMLInputElement ? this.options.element.parentNode.appendChild(this.picker) : this.options.element.appendChild(this.picker) : document.body.appendChild(this.picker), this.options.mobileFriendly && (this.backdrop = document.createElement("div"), this.backdrop.className = s.litepickerBackdrop, this.backdrop.addEventListener("click", this.hide()), this.options.element && this.options.element.parentNode && this.options.element.parentNode.appendChild(this.backdrop), window.addEventListener("orientationchange", () => {
                    if (this.options.mobileFriendly && this.isShowning()) {
                        switch (screen.orientation.angle) {
                            case -90:
                            case 90:
                                this.options.numberOfMonths = 2, this.options.numberOfColumns = 2;
                                break;
                            default:
                                this.options.numberOfMonths = 1, this.options.numberOfColumns = 1
                        }
                        this.render();
                        const t = this.picker.getBoundingClientRect();
                        this.picker.style.top = `calc(50% - ${t.height/2}px)`, this.picker.style.left = `calc(50% - ${t.width/2}px)`
                    }
                })), this.options.inlineMode && this.show(), this.updateInput()
            }
            parseInput() {
            	

                if (this.options.elementEnd) {
                    if (this.options.element instanceof HTMLInputElement && this.options.element.value.length && this.options.elementEnd instanceof HTMLInputElement && this.options.elementEnd.value.length) return [new n.DateTime(this.options.element.value), new n.DateTime(this.options.elementEnd.value)]
                } else if (this.options.singleMode) {
                    if (this.options.element instanceof HTMLInputElement && this.options.element.value.length) return [new n.DateTime(this.options.element.value)]
                } else if (/\s\-\s/.test(this.options.element.value)) {
                    const t = this.options.element.value.split(" - ");
                    if (2 === t.length) return [new n.DateTime(t[0]), new n.DateTime(t[1])]
                }
                return []
            }
            updateInput() {
            	

                if (this.options.element instanceof HTMLInputElement)
                    if (this.options.singleMode && this.options.startDate) this.options.element.value = this.options.startDate.format(this.options.format, this.options.lang);
                    else if (!this.options.singleMode && this.options.startDate && this.options.endDate) {
                    const t = this.options.startDate.format(this.options.format, this.options.lang),
                        e = this.options.endDate.format(this.options.format, this.options.lang);
                    this.options.elementEnd ? (this.options.element.value = t, this.options.elementEnd.value = e) : this.options.element.value = `${t} - ${e}`
                }
            }
            isSamePicker(t) {
            	

                return t.closest(`.${s.litepicker}`) === this.picker
            }
            shouldShown(t) {
            	

                return t === this.options.element || this.options.elementEnd && t === this.options.elementEnd
            }
            shouldResetDatePicked() {
            	

                return this.options.singleMode || 2 === this.datePicked.length
            }
            shouldSwapDatePicked() {
            	

                return 2 === this.datePicked.length && this.datePicked[0].getTime() > this.datePicked[1].getTime()
            }
            shouldCheckLockDays() {
            	

                return this.options.disallowLockDaysInRange && this.options.lockDays.length && 2 === this.datePicked.length
            }
            onClick(t) {
            	

                const e = t.target;
                if (e && this.picker)
                    if (this.shouldShown(e)) this.show(e);
                    else if (e.closest(`.${s.litepicker}`)) {
                    if (e.classList.contains(s.dayItem)) {
                        if (t.preventDefault(), !this.isSamePicker(e)) return;
                        if (e.classList.contains(s.isLocked)) return;
                        if (e.classList.contains(s.isBooked)) return;
                        if (this.shouldResetDatePicked() && (this.datePicked.length = 0), this.datePicked[this.datePicked.length] = new n.DateTime(e.dataset.time), this.shouldSwapDatePicked()) {
                            const t = this.datePicked[1].clone();
                            this.datePicked[1] = this.datePicked[0].clone(), this.datePicked[0] = t.clone()
                        }
                        if (this.shouldCheckLockDays()) {
                            this.options.lockDays.filter(t => t instanceof Array ? t[0].isBetween(this.datePicked[0], this.datePicked[1]) || t[1].isBetween(this.datePicked[0], this.datePicked[1]) : t.isBetween(this.datePicked[0], this.datePicked[1])).length && (this.datePicked.length = 0, "function" == typeof this.options.onError && this.options.onError.call(this, "INVALID_RANGE"))
                        }
                        return this.render(), void(this.options.autoApply && (this.options.singleMode && this.datePicked.length ? (this.setDate(this.datePicked[0]), this.hide()) : this.options.singleMode || 2 !== this.datePicked.length || (this.setDateRange(this.datePicked[0], this.datePicked[1]), this.hide())))
                    }
                    if (e.classList.contains(s.buttonPreviousMonth)) {
                        if (t.preventDefault(), !this.isSamePicker(e)) return;
                        let i = 0,
                            o = this.options.numberOfMonths;
                        if (this.options.splitView) {
                            const t = e.closest(`.${s.monthItem}`);
                            i = [...t.parentNode.childNodes].findIndex(e => e === t), o = 1
                        }
                        return this.calendars[i].setMonth(this.calendars[i].getMonth() - o), this.gotoDate(this.calendars[i], i), void("function" == typeof this.options.onChangeMonth && this.options.onChangeMonth.call(this, this.calendars[i], i))
                    }
                    if (e.classList.contains(s.buttonNextMonth)) {
                        if (t.preventDefault(), !this.isSamePicker(e)) return;
                        let i = 0,
                            o = this.options.numberOfMonths;
                        if (this.options.splitView) {
                            const t = e.closest(`.${s.monthItem}`);
                            i = [...t.parentNode.childNodes].findIndex(e => e === t), o = 1
                        }
                        return this.calendars[i].setMonth(this.calendars[i].getMonth() + o), this.gotoDate(this.calendars[i], i), void("function" == typeof this.options.onChangeMonth && this.options.onChangeMonth.call(this, this.calendars[i], i))
                    }
                    if (e.classList.contains(s.buttonCancel)) {
                        if (t.preventDefault(), !this.isSamePicker(e)) return;
                        this.hide()
                    }
                    if (e.classList.contains(s.buttonApply)) {
                        if (t.preventDefault(), !this.isSamePicker(e)) return;
                        this.options.singleMode && this.datePicked.length ? this.setDate(this.datePicked[0]) : this.options.singleMode || 2 !== this.datePicked.length || this.setDateRange(this.datePicked[0], this.datePicked[1]), this.hide()
                    }
                } else this.hide()
            }
            showTooltip(t, e) {
                const i = this.picker.querySelector(`.${s.containerTooltip}`);
                i.style.visibility = "visible", i.innerHTML = e;
                const o = this.picker.getBoundingClientRect(),
                    n = i.getBoundingClientRect(),
                    a = t.getBoundingClientRect();
                let r = a.top,
                    l = a.left;
                if (this.options.inlineMode && this.options.parentEl) {
                    const t = this.picker.parentNode.getBoundingClientRect();
                    r -= t.top, l -= t.left
                } else r -= o.top, l -= o.left;
                r -= n.height, l -= n.width / 2, l += a.width / 2, i.style.top = `${r}px`, i.style.left = `${l}px`
            }
            hideTooltip() {
                this.picker.querySelector(`.${s.containerTooltip}`).style.visibility = "hidden"
            }
            shouldAllowMouseEnter(t) {


                return !this.options.singleMode && t.classList.contains(s.dayItem) && !t.classList.contains(s.isLocked) && !t.classList.contains(s.isBooked)
            }
            shouldAllowRepick() {


                return this.options.elementEnd && this.options.allowRepick && this.options.startDate && this.options.endDate
            }
            onMouseEnter(t) {
            	

                const e = t.target;

            	//Chiamata alla funzione di datePickerMod.js per verifica turno
            	
                rimuoviTurno();
				if (typeof e.dataset.time !== 'undefined'){
					var data = new n.DateTime(e.dataset.time); 
					calcolaTurno(data);
				}
				
            	//fine chiamata
            	
                if (this.shouldAllowMouseEnter(e)) {
                    if (this.shouldAllowRepick() && (this.triggerElement === this.options.element ? this.datePicked[0] = this.options.endDate.clone() : this.datePicked[0] = this.options.startDate.clone()), 1 !== this.datePicked.length) return;
                    const t = this.picker.querySelector(`.${s.dayItem}[data-time="${this.datePicked[0].getTime()}"]`);
                    let i = this.datePicked[0].clone(),
                        o = new n.DateTime(e.dataset.time),
                        a = !1;
                    if (i.getTime() > o.getTime()) {
                        const t = i.clone();
                        i = o.clone(), o = t.clone(), a = !0
                    }
                    if ([...this.picker.querySelectorAll(`.${s.dayItem}`)].forEach(t => {
                            const e = new n.DateTime(t.dataset.time),
                                a = this.renderDay(e);
                            e.isBetween(i, o) && a.classList.add(s.isInRange), t.className = a.className
                        }), e.classList.add(s.isEndDate), a ? (t && t.classList.add(s.isFlipped), e.classList.add(s.isFlipped)) : (t && t.classList.remove(s.isFlipped), e.classList.remove(s.isFlipped)), this.options.showTooltip) {
                        const t = new Intl.PluralRules(this.options.lang);
                        let n = o.diff(i, "day");
                        if (this.options.hotelMode || (n += 1), n > 0) {
                            const i = t.select(n),
                                o = `${n} ${this.options.tooltipText[i]?this.options.tooltipText[i]:`[${i}]`}`;
                            this.showTooltip(e, o)
                        } else this.hideTooltip()
                    }
                }
            }
            onMouseLeave(t) {
            	

            	//Chiamata alla funzione di datePickerMod.js per rimozione turno
            	
            	rimuoviTurno();
				
            	//
            	
                t.target;
                this.options.allowRepick && (this.datePicked.length = 0, this.render())
            }
            onKeyDown(t) {
            	

                const e = t.target;
                switch (t.code) {
                    case "ArrowUp":
                        if (e.classList.contains(s.dayItem)) {
                            t.preventDefault();
                            const i = [...e.parentNode.childNodes].findIndex(t => t === e) - 7;
                            i > 0 && e.parentNode.childNodes[i] && e.parentNode.childNodes[i].focus()
                        }
                        break;
                    case "ArrowLeft":
                        e.classList.contains(s.dayItem) && e.previousSibling && (t.preventDefault(), e.previousSibling.focus());
                        break;
                    case "ArrowRight":
                        e.classList.contains(s.dayItem) && e.nextSibling && (t.preventDefault(), e.nextSibling.focus());
                        break;
                    case "ArrowDown":
                        if (e.classList.contains(s.dayItem)) {
                            t.preventDefault();
                            const i = [...e.parentNode.childNodes].findIndex(t => t === e) + 7;
                            i > 0 && e.parentNode.childNodes[i] && e.parentNode.childNodes[i].focus()
                        }
                }
            }
            onInput(t) {
            	

                let [e, i] = this.parseInput();
                if (e instanceof Date && !isNaN(e.getTime()) && i instanceof Date && !isNaN(i.getTime())) {
                    if (e.getTime() > i.getTime()) {
                        const t = e.clone();
                        e = i.clone(), i = t.clone()
                    }
                    this.options.startDate = new n.DateTime(e, this.options.format, this.options.lang), this.options.startDate && (this.options.endDate = new n.DateTime(i, this.options.format, this.options.lang)), this.updateInput(), this.render()
                }
            }
            isShowning() {

            	
                return this.picker && "none" !== this.picker.style.display
            }
        }
        e.Litepicker = a
    }, function(t, e, i) {
        "use strict";
        Object.defineProperty(e, "__esModule", {
            value: !0
        });
        const o = i(2);
        e.Litepicker = o.Litepicker, i(8)
    }, function(t, e, i) {
        "use strict";
        Object.defineProperty(e, "__esModule", {
            value: !0
        });
        const o = i(0),
            n = i(1);
        e.Calendar = class {
            constructor() {
                this.options = {
                    element: null,
                    elementEnd: null,
                    parentEl: null,
                    firstDay: 1,
                    format: "YYYY-MM-DD",
                    lang: "en-US",
                    numberOfMonths: 1,
                    numberOfColumns: 1,
                    startDate: null,
                    endDate: null,
                    zIndex: 9999,
                    minDate: null,
                    maxDate: null,
                    minDays: null,
                    maxDays: null,
                    selectForward: !1,
                    selectBackward: !1,
                    splitView: !1,
                    inlineMode: !1,
                    singleMode: !0,
                    autoApply: !0,
                    allowRepick: !1,
                    showWeekNumbers: !1,
                    showTooltip: !0,
                    hotelMode: !1,
                    disableWeekends: !1,
                    scrollToDate: !0,
                    mobileFriendly: !0,
                    lockDaysFormat: "YYYY-MM-DD",
                    lockDays: [],
                    disallowLockDaysInRange: !1,
                    bookedDaysFormat: "YYYY-MM-DD",
                    bookedDays: [],
                    buttonText: {
                        apply: "Apply",
                        cancel: "Cancel",
                        previousMonth: '<svg width="11" height="16" xmlns="http://www.w3.org/2000/svg"><path d="M7.919 0l2.748 2.667L5.333 8l5.334 5.333L7.919 16 0 8z" fill-rule="nonzero"/></svg>',
                        nextMonth: '<svg width="11" height="16" xmlns="http://www.w3.org/2000/svg"><path d="M2.748 16L0 13.333 5.333 8 0 2.667 2.748 0l7.919 8z" fill-rule="nonzero"/></svg>'
                    },
                    tooltipText: {
                        one: "day",
                        other: "days"
                    },
                    onShow: null,
                    onHide: null,
                    onSelect: null,
                    onError: null,
                    onChangeMonth: null,
                    onChangeYear: null
                }, this.calendars = [], this.datePicked = []
            }
            render() {
            	 

                const t = document.createElement("div");
                t.className = n.containerMonths, n[`columns${this.options.numberOfColumns}`] && (t.classList.remove(n.columns2, n.columns3, n.columns4), t.classList.add(n[`columns${this.options.numberOfColumns}`])), this.options.splitView && t.classList.add(n.splitView), this.options.showWeekNumbers && t.classList.add(n.showWeekNumbers);
                const e = this.calendars[0].clone(),
                    i = e.getMonth(),
                    o = e.getMonth() + this.options.numberOfMonths;
                let s = 0;
                for (let n = i; n < o; n += 1) {
                    let i = e.clone();
                    this.options.splitView ? i = this.calendars[s].clone() : i.setMonth(n), t.appendChild(this.renderMonth(i)), s += 1
                }
                this.picker.innerHTML = "", this.picker.appendChild(t), this.options.autoApply && !this.options.footerHTML || this.picker.appendChild(this.renderFooter()), this.options.showTooltip && this.picker.appendChild(this.renderTooltip());
            }
            renderMonth(t) {
            	 

                const e = t.clone();
                e.setDate(1);
                const i = 32 - new Date(e.getFullYear(), e.getMonth(), 32).getDate(),
                    s = document.createElement("div");
                s.className = n.monthItem;
                const a = document.createElement("div");
                a.className = n.monthItemHeader, a.innerHTML = `\n      <a href="#" class="${n.buttonPreviousMonth}">${this.options.buttonText.previousMonth}</a>\n      <div>\n        <strong>${t.toLocaleString(this.options.lang,{month:"long"})}</strong>\n        ${t.getFullYear()}\n      </div>\n      <a href="#" class="${n.buttonNextMonth}">${this.options.buttonText.nextMonth}</a>\n    `, this.options.minDate && e.isSameOrBefore(new o.DateTime(this.options.minDate), "month") && s.classList.add(n.noPreviousMonth), this.options.maxDate && e.isSameOrAfter(new o.DateTime(this.options.maxDate), "month") && s.classList.add(n.noNextMonth);
                const r = document.createElement("div");
                r.className = n.monthItemWeekdaysRow, this.options.showWeekNumbers && (r.innerHTML = "<div>W</div>");
                for (let t = 1; t <= 7; t += 1) {
                    const e = 3 + this.options.firstDay + t,
                        i = document.createElement("div");
                    i.innerHTML = this.weekdayName(e), i.title = this.weekdayName(e, "long"), r.appendChild(i)
                }
                const l = document.createElement("div");
                l.className = n.containerDays;
                const c = this.calcSkipDays(e);
                this.options.showWeekNumbers && c && l.appendChild(this.renderWeekNumber(e));
                for (let t = 0; t < c; t += 1) {
                    const t = document.createElement("div");
                    l.appendChild(t)
                }
                for (let t = 1; t <= i; t += 1) e.setDate(t), this.options.showWeekNumbers && e.getDay() === this.options.firstDay && l.appendChild(this.renderWeekNumber(e)), l.appendChild(this.renderDay(e));
                return s.appendChild(a), s.appendChild(r), s.appendChild(l), s
            }
            renderDay(t) {
            	 

                const e = document.createElement("a");
                if (e.href = "#", e.className = n.dayItem, e.innerHTML = String(t.getDate()), e.dataset.time = String(t.getTime()), t.toDateString() === (new Date).toDateString() && e.classList.add(n.isToday), this.datePicked.length ? (this.datePicked[0].toDateString() === t.toDateString() && (e.classList.add(n.isStartDate), this.options.singleMode && e.classList.add(n.isEndDate)), 2 === this.datePicked.length && this.datePicked[1].toDateString() === t.toDateString() && e.classList.add(n.isEndDate), 2 === this.datePicked.length && t.isBetween(this.datePicked[0], this.datePicked[1]) && e.classList.add(n.isInRange)) : this.options.startDate && (this.options.startDate.toDateString() === t.toDateString() && (e.classList.add(n.isStartDate), this.options.singleMode && e.classList.add(n.isEndDate)), this.options.endDate && this.options.endDate.toDateString() === t.toDateString() && e.classList.add(n.isEndDate), this.options.startDate && this.options.endDate && t.isBetween(this.options.startDate, this.options.endDate) && e.classList.add(n.isInRange)), this.options.minDate && t.isBefore(new o.DateTime(this.options.minDate)) && e.classList.add(n.isLocked), this.options.maxDate && t.isAfter(new o.DateTime(this.options.maxDate)) && e.classList.add(n.isLocked), this.options.minDays && 1 === this.datePicked.length) {
                    const i = this.datePicked[0].clone().subtract(this.options.minDays, "day"),
                        o = this.datePicked[0].clone().add(this.options.minDays, "day");
                    t.isBetween(i, this.datePicked[0], "(]") && e.classList.add(n.isLocked), t.isBetween(this.datePicked[0], o, "[)") && e.classList.add(n.isLocked)
                }
                if (this.options.maxDays && 1 === this.datePicked.length) {
                    const i = this.datePicked[0].clone().subtract(this.options.maxDays, "day"),
                        o = this.datePicked[0].clone().add(this.options.maxDays, "day");
                    t.isBefore(i) && e.classList.add(n.isLocked), t.isAfter(o) && e.classList.add(n.isLocked)
                }
                if (this.options.selectForward && 1 === this.datePicked.length && t.isBefore(this.datePicked[0]) && e.classList.add(n.isLocked), this.options.selectBackward && 1 === this.datePicked.length && t.isAfter(this.datePicked[0]) && e.classList.add(n.isLocked), this.options.lockDays.length) {
                    this.options.lockDays.filter(e => e instanceof Array ? t.isBetween(e[0], e[1]) : e.isSame(t, "day")).length && e.classList.add(n.isLocked)
                }
                if (this.datePicked.length <= 1 && this.options.bookedDays.length) {
                    const i = this.options.bookedDays.filter(e => e instanceof Array ? t.isBetween(e[0], e[1]) : e.isSame(t, "day")).length,
                        o = 0 === this.datePicked.length || t.isBefore(this.datePicked[0]);
                    i && o && e.classList.add(n.isBooked)
                }
                return !this.options.disableWeekends || 6 !== t.getDay() && 0 !== t.getDay() || e.classList.add(n.isLocked), e
            }
            renderFooter() {
 

                const t = document.createElement("div");
                if (t.className = n.containerFooter, this.options.footerHTML ? t.innerHTML = this.options.footerHTML : t.innerHTML = `\n      <span class="${n.previewDateRange}"></span>\n      <button type="button" class="${n.buttonCancel}">${this.options.buttonText.cancel}</button>\n      <button type="button" class="${n.buttonApply}">${this.options.buttonText.apply}</button>\n      `, this.options.singleMode) {
                    if (1 === this.datePicked.length) {
                        const e = this.datePicked[0].format(this.options.format, this.options.lang);
                        t.querySelector(`.${n.previewDateRange}`).innerHTML = e
                    }
                } else if (1 === this.datePicked.length && t.querySelector(`.${n.buttonApply}`).setAttribute("disabled", ""), 2 === this.datePicked.length) {
                    const e = this.datePicked[0].format(this.options.format, this.options.lang),
                        i = this.datePicked[1].format(this.options.format, this.options.lang);
                    t.querySelector(`.${n.previewDateRange}`).innerHTML = `${e} - ${i}`
                }
                return t
            }
            renderWeekNumber(t) {
                const e = document.createElement("div"),
                    i = t.getWeek(this.options.firstDay);
                return e.className = n.weekNumber, e.innerHTML = 53 === i && 0 === t.getMonth() ? "53 / 1" : i, e
            }
            renderTooltip() {
                const t = document.createElement("div");
                return t.className = n.containerTooltip, t
            }
            weekdayName(t, e = "short") {
                return new Date(1970, 0, t, 12, 0, 0, 0).toLocaleString(this.options.lang, {
                    weekday: e
                })
            }
            calcSkipDays(t) {
            	

                let e = t.getDay() - this.options.firstDay;
                return e < 0 && (e += 7), e
            }
        }
    }, function(t, e, i) {
        (e = t.exports = i(6)(!1)).push([t.i, ':root{--litepickerBgColor: #fff;--litepickerMonthHeaderTextColor: #333;--litepickerMonthButton: #9e9e9e;--litepickerMonthButtonHover: #2196f3;--litepickerMonthWidth: calc(var(--litepickerDayWidth) * 7);--litepickerMonthWeekdayColor: #9e9e9e;--litepickerDayColor: #007BFF;--litepickerDayColorHover: #2196f3;--litepickerDayIsTodayColor: #f44336;--litepickerDayIsInRange: #bbdefb;--litepickerDayIsLockedColor: #9e9e9e;--litepickerDayIsBookedColor: #9e9e9e;--litepickerDayIsStartColor: #fff;--litepickerDayIsStartBg: #2196f3;--litepickerDayIsEndColor: #fff;--litepickerDayIsEndBg: #2196f3;--litepickerDayWidth: 38px;--litepickerButtonCancelColor: #fff;--litepickerButtonCancelBg: #9e9e9e;--litepickerButtonApplyColor: #fff;--litepickerButtonApplyBg: #2196f3}.show-week-numbers{--litepickerMonthWidth: calc(var(--litepickerDayWidth) * 8)}.litepicker{font-family:-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;font-size:0.8em;display:none}.litepicker .container__months{display:-webkit-box;display:-ms-flexbox;display:flex;-ms-flex-wrap:wrap;flex-wrap:wrap;background-color:var(--litepickerBgColor);border-radius:5px;-webkit-box-shadow:0 0 5px #ddd;box-shadow:0 0 5px #ddd;width:calc(var(--litepickerMonthWidth) + 10px);-webkit-box-sizing:content-box;box-sizing:content-box}.litepicker .container__months.columns-2{width:calc((var(--litepickerMonthWidth) * 2) + 20px)}.litepicker .container__months.columns-3{width:calc((var(--litepickerMonthWidth) * 3) + 30px)}.litepicker .container__months.columns-4{width:calc((var(--litepickerMonthWidth) * 4) + 40px)}.litepicker .container__months.split-view .month-item-header .button-previous-month,.litepicker .container__months.split-view .month-item-header .button-next-month{visibility:visible}.litepicker .container__months .month-item{padding:5px;width:var(--litepickerMonthWidth)}.litepicker .container__months .month-item-header{font-size:150%;display:-webkit-box;display:-ms-flexbox;display:flex;-webkit-box-pack:space-evenly;-ms-flex-pack:space-evenly;justify-content:space-evenly;font-weight:500;padding:10px 5px;text-align:center;-webkit-box-align:center;-ms-flex-align:center;align-items:center;color:var(--litepickerMonthHeaderTextColor)}.litepicker .container__months .month-item-header .button-previous-month,.litepicker .container__months .month-item-header .button-next-month{visibility:hidden;text-decoration:none;color:var(--litepickerMonthButton);padding:3px 5px;border-radius:3px;-webkit-transition:color 0.3s, border 0.3s;transition:color 0.3s, border 0.3s;cursor:default}.litepicker .container__months .month-item-header .button-previous-month>svg,.litepicker .container__months .month-item-header .button-previous-month>img,.litepicker .container__months .month-item-header .button-next-month>svg,.litepicker .container__months .month-item-header .button-next-month>img{fill:var(--litepickerMonthButton);pointer-events:none}.litepicker .container__months .month-item-header .button-previous-month:hover,.litepicker .container__months .month-item-header .button-next-month:hover{color:var(--litepickerMonthButtonHover)}.litepicker .container__months .month-item-header .button-previous-month:hover>svg,.litepicker .container__months .month-item-header .button-next-month:hover>svg{fill:var(--litepickerMonthButtonHover)}.litepicker .container__months .month-item-weekdays-row{display:-webkit-box;display:-ms-flexbox;display:flex;justify-self:center;-webkit-box-pack:start;-ms-flex-pack:start;justify-content:flex-start;color:var(--litepickerMonthWeekdayColor)}.litepicker .container__months .month-item-weekdays-row>div{padding:5px 0;font-size:120%;-webkit-box-flex:1;-ms-flex:1;flex:1;width:var(--litepickerDayWidth)}.litepicker .container__months .month-item:first-child .button-previous-month{visibility:visible}.litepicker .container__months .month-item:last-child .button-next-month{visibility:visible}.litepicker .container__months .month-item.no-previous-month .button-previous-month{visibility:hidden}.litepicker .container__months .month-item.no-next-month .button-next-month{visibility:hidden}.litepicker .container__days{display:-webkit-box;display:-ms-flexbox;display:flex;-ms-flex-wrap:wrap;flex-wrap:wrap;justify-self:center;-webkit-box-pack:start;-ms-flex-pack:start;justify-content:flex-start;text-align:center}.litepicker .container__days>div,.litepicker .container__days>a{padding:5px 0;width:var(--litepickerDayWidth)}.litepicker .container__days .day-item{color:var(--litepickerDayColor);font-weight: bold;text-align:center;border:1px solid #d1d1d1;font-size: 150%;text-decoration:none;border-radius:3px;-webkit-transition:color 0.3s, border 0.3s;transition:color 0.3s, border 0.3s;cursor:default}.litepicker .container__days .day-item:hover{color:var(--litepickerDayColorHover);-webkit-box-shadow:inset 0 0 0 1px var(--litepickerDayColorHover);box-shadow:inset 0 0 0 1px var(--litepickerDayColorHover)}.litepicker .container__days .day-item.is-today{color:var(--litepickerDayIsTodayColor)}.litepicker .container__days .day-item.is-locked{color:var(--litepickerDayIsLockedColor);pointer-events:none}.litepicker .container__days .day-item.is-locked:hover{color:var(--litepickerDayIsLockedColor);-webkit-box-shadow:none;box-shadow:none;cursor:default}.litepicker .container__days .day-item.is-booked{color:var(--litepickerDayIsBookedColor);pointer-events:none}.litepicker .container__days .day-item.is-booked:hover{color:var(--litepickerDayIsBookedColor);-webkit-box-shadow:none;box-shadow:none;cursor:default}.litepicker .container__days .day-item.is-in-range{background-color:var(--litepickerDayIsInRange);border-radius:0}.litepicker .container__days .day-item.is-start-date{color:var(--litepickerDayIsStartColor);background-color:var(--litepickerDayIsStartBg);border-top-left-radius:5px;border-bottom-left-radius:5px;border-top-right-radius:0;border-bottom-right-radius:0}.litepicker .container__days .day-item.is-start-date.is-flipped{border-top-left-radius:0;border-bottom-left-radius:0;border-top-right-radius:5px;border-bottom-right-radius:5px}.litepicker .container__days .day-item.is-end-date{color:var(--litepickerDayIsEndColor);background-color:var(--litepickerDayIsEndBg);border-top-left-radius:0;border-bottom-left-radius:0;border-top-right-radius:5px;border-bottom-right-radius:5px}.litepicker .container__days .day-item.is-end-date.is-flipped{border-top-left-radius:5px;border-bottom-left-radius:5px;border-top-right-radius:0;border-bottom-right-radius:0}.litepicker .container__days .day-item.is-start-date.is-end-date{border-top-left-radius:5px;border-bottom-left-radius:5px;border-top-right-radius:5px;border-bottom-right-radius:5px}.litepicker .container__days .week-number{display:-webkit-box;display:-ms-flexbox;display:flex;-webkit-box-align:center;-ms-flex-align:center;align-items:center;-webkit-box-pack:center;-ms-flex-pack:center;justify-content:center;color:#9e9e9e;font-size:85%}.litepicker .container__footer{text-align:right;padding:10px 5px;margin:0 5px;background-color:#fafafa;-webkit-box-shadow:inset 0px 3px 3px 0px #ddd;box-shadow:inset 0px 3px 3px 0px #ddd;border-bottom-left-radius:5px;border-bottom-right-radius:5px}.litepicker .container__footer .preview-date-range{margin-right:10px;font-size:90%}.litepicker .container__footer .button-cancel{background-color:var(--litepickerButtonCancelBg);color:var(--litepickerButtonCancelColor);border:0;padding:3px 7px 4px;border-radius:3px}.litepicker .container__footer .button-cancel>svg,.litepicker .container__footer .button-cancel>img{pointer-events:none}.litepicker .container__footer .button-apply{background-color:var(--litepickerButtonApplyBg);color:var(--litepickerButtonApplyColor);border:0;padding:3px 7px 4px;border-radius:3px;margin-left:10px;margin-right:10px}.litepicker .container__footer .button-apply:disabled{opacity:0.7}.litepicker .container__footer .button-apply>svg,.litepicker .container__footer .button-apply>img{pointer-events:none}.litepicker .container__tooltip{position:absolute;margin-top:-4px;padding:4px 8px;border-radius:4px;background-color:#fff;-webkit-box-shadow:0 1px 3px rgba(0,0,0,0.25);box-shadow:0 1px 3px rgba(0,0,0,0.25);white-space:nowrap;font-size:11px;pointer-events:none;visibility:hidden}.litepicker .container__tooltip:before{position:absolute;bottom:-5px;left:calc(50% - 5px);border-top:5px solid rgba(0,0,0,0.12);border-right:5px solid transparent;border-left:5px solid transparent;content:""}.litepicker .container__tooltip:after{position:absolute;bottom:-4px;left:calc(50% - 4px);border-top:4px solid #fff;border-right:4px solid transparent;border-left:4px solid transparent;content:""}.litepicker-open{overflow:hidden}.litepicker-backdrop{display:none;background-color:#000;opacity:0.3;position:fixed;top:0;right:0;bottom:0;left:0}\n', ""]), e.locals = {
            showWeekNumbers: "show-week-numbers",
            litepicker: "litepicker",
            containerMonths: "container__months",
            columns2: "columns-2",
            columns3: "columns-3",
            columns4: "columns-4",
            splitView: "split-view",
            monthItemHeader: "month-item-header",
            buttonPreviousMonth: "button-previous-month",
            buttonNextMonth: "button-next-month",
            monthItem: "month-item",
            monthItemWeekdaysRow: "month-item-weekdays-row",
            noPreviousMonth: "no-previous-month",
            noNextMonth: "no-next-month",
            containerDays: "container__days",
            dayItem: "day-item",
            isToday: "is-today",
            isLocked: "is-locked",
            isBooked: "is-booked",
            isInRange: "is-in-range",
            isStartDate: "is-start-date",
            isFlipped: "is-flipped",
            isEndDate: "is-end-date",
            weekNumber: "week-number",
            containerFooter: "container__footer",
            previewDateRange: "preview-date-range",
            buttonCancel: "button-cancel",
            buttonApply: "button-apply",
            containerTooltip: "container__tooltip",
            litepickerOpen: "litepicker-open",
            litepickerBackdrop: "litepicker-backdrop"
        }
    }, function(t, e, i) {
        "use strict";
        t.exports = function(t) {
            var e = [];
            return e.toString = function() {
                return this.map((function(e) {
                    var i = function(t, e) {
                        var i = t[1] || "",
                            o = t[3];
                        if (!o) return i;
                        if (e && "function" == typeof btoa) {
                            var n = (a = o, r = btoa(unescape(encodeURIComponent(JSON.stringify(a)))), l = "sourceMappingURL=data:application/json;charset=utf-8;base64,".concat(r), "/*# ".concat(l, " */")),
                                s = o.sources.map((function(t) {
                                    return "/*# sourceURL=".concat(o.sourceRoot).concat(t, " */")
                                }));
                            return [i].concat(s).concat([n]).join("\n")
                        }
                        var a, r, l;
                        return [i].join("\n")
                    }(e, t);
                    return e[2] ? "@media ".concat(e[2], "{").concat(i, "}") : i
                })).join("")
            }, e.i = function(t, i) {
                "string" == typeof t && (t = [
                    [null, t, ""]
                ]);
                for (var o = {}, n = 0; n < this.length; n++) {
                    var s = this[n][0];
                    null != s && (o[s] = !0)
                }
                for (var a = 0; a < t.length; a++) {
                    var r = t[a];
                    null != r[0] && o[r[0]] || (i && !r[2] ? r[2] = i : i && (r[2] = "(".concat(r[2], ") and (").concat(i, ")")), e.push(r))
                }
            }, e
        }
    }, function(t, e, i) {
        "use strict";
        var o, n = {},
            s = function() {
                return void 0 === o && (o = Boolean(window && document && document.all && !window.atob)), o
            },
            a = function() {
                var t = {};
                return function(e) {
                    if (void 0 === t[e]) {
                        var i = document.querySelector(e);
                        if (window.HTMLIFrameElement && i instanceof window.HTMLIFrameElement) try {
                            i = i.contentDocument.head
                        } catch (t) {
                            i = null
                        }
                        t[e] = i
                    }
                    return t[e]
                }
            }();

        function r(t, e) {
            for (var i = [], o = {}, n = 0; n < t.length; n++) {
                var s = t[n],
                    a = e.base ? s[0] + e.base : s[0],
                    r = {
                        css: s[1],
                        media: s[2],
                        sourceMap: s[3]
                    };
                o[a] ? o[a].parts.push(r) : i.push(o[a] = {
                    id: a,
                    parts: [r]
                })
            }
            return i
        }

        function l(t, e) {
            for (var i = 0; i < t.length; i++) {
                var o = t[i],
                    s = n[o.id],
                    a = 0;
                if (s) {
                    for (s.refs++; a < s.parts.length; a++) s.parts[a](o.parts[a]);
                    for (; a < o.parts.length; a++) s.parts.push(f(o.parts[a], e))
                } else {
                    for (var r = []; a < o.parts.length; a++) r.push(f(o.parts[a], e));
                    n[o.id] = {
                        id: o.id,
                        refs: 1,
                        parts: r
                    }
                }
            }
        }

        function c(t) {
            var e = document.createElement("style");
            if (void 0 === t.attributes.nonce) {
                var o = i.nc;
                o && (t.attributes.nonce = o)
            }
            if (Object.keys(t.attributes).forEach((function(i) {
                    e.setAttribute(i, t.attributes[i])
                })), "function" == typeof t.insert) t.insert(e);
            else {
                var n = a(t.insert || "head");
                if (!n) throw new Error("Couldn't find a style target. This probably means that the value for the 'insert' parameter is invalid.");
                n.appendChild(e)
            }
            return e
        }
        var h, d = (h = [], function(t, e) {
            return h[t] = e, h.filter(Boolean).join("\n")
        });

        function p(t, e, i, o) {
            var n = i ? "" : o.css;
            if (t.styleSheet) t.styleSheet.cssText = d(e, n);
            else {
                var s = document.createTextNode(n),
                    a = t.childNodes;
                a[e] && t.removeChild(a[e]), a.length ? t.insertBefore(s, a[e]) : t.appendChild(s)
            }
        }

        function u(t, e, i) {
            var o = i.css,
                n = i.media,
                s = i.sourceMap;
            if (n && t.setAttribute("media", n), s && btoa && (o += "\n/*# sourceMappingURL=data:application/json;base64,".concat(btoa(unescape(encodeURIComponent(JSON.stringify(s)))), " */")), t.styleSheet) t.styleSheet.cssText = o;
            else {
                for (; t.firstChild;) t.removeChild(t.firstChild);
                t.appendChild(document.createTextNode(o))
            }
        }
        var m = null,
            g = 0;

        function f(t, e) {
            var i, o, n;
            if (e.singleton) {
                var s = g++;
                i = m || (m = c(e)), o = p.bind(null, i, s, !1), n = p.bind(null, i, s, !0)
            } else i = c(e), o = u.bind(null, i, e), n = function() {
                ! function(t) {
                    if (null === t.parentNode) return !1;
                    t.parentNode.removeChild(t)
                }(i)
            };
            return o(t),
                function(e) {
                    if (e) {
                        if (e.css === t.css && e.media === t.media && e.sourceMap === t.sourceMap) return;
                        o(t = e)
                    } else n()
                }
        }
        t.exports = function(t, e) {
            (e = e || {}).attributes = "object" == typeof e.attributes ? e.attributes : {}, e.singleton || "boolean" == typeof e.singleton || (e.singleton = s());
            var i = r(t, e);
            return l(i, e),
                function(t) {
                    for (var o = [], s = 0; s < i.length; s++) {
                        var a = i[s],
                            c = n[a.id];
                        c && (c.refs--, o.push(c))
                    }
                    t && l(r(t, e), e);
                    for (var h = 0; h < o.length; h++) {
                        var d = o[h];
                        if (0 === d.refs) {
                            for (var p = 0; p < d.parts.length; p++) d.parts[p]();
                            delete n[d.id]
                        }
                    }
                }
        }
    }, function(t, e, i) {
        "use strict";
        Object.defineProperty(e, "__esModule", {
            value: !0
        });
        const o = i(0),
            n = i(2),
            s = i(1),
            a = i(9);
        n.Litepicker.prototype.show = function(t = null) {
            if (this.options.inlineMode) return this.picker.style.position = "static", this.picker.style.display = "inline-block", this.picker.style.top = null, this.picker.style.left = null, this.picker.style.bottom = null, void(this.picker.style.right = null);
            if (this.options.scrollToDate && (!this.options.startDate || t && t !== this.options.element ? t && this.options.endDate && t === this.options.elementEnd && (this.calendars[0] = this.options.endDate.clone()) : this.calendars[0] = this.options.startDate.clone()), this.options.mobileFriendly && a.isMobile()) {
                this.picker.style.position = "fixed", this.picker.style.display = "block", "portrait" === a.getOrientation() ? (this.options.numberOfMonths = 1, this.options.numberOfColumns = 1) : (this.options.numberOfMonths = 2, this.options.numberOfColumns = 2), this.render();
                const e = this.picker.getBoundingClientRect();
                return this.picker.style.top = `calc(50% - ${e.height/2}px)`, this.picker.style.left = `calc(50% - ${e.width/2}px)`, this.picker.style.right = null, this.picker.style.bottom = null, this.picker.style.zIndex = this.options.zIndex, this.backdrop.style.display = "block", this.backdrop.style.zIndex = this.options.zIndex - 1, document.body.classList.add(s.litepickerOpen), "function" == typeof this.options.onShow && this.options.onShow.call(this), void(t ? t.blur() : this.options.element.blur())
            }
            this.render(), this.picker.style.position = "absolute", this.picker.style.display = "block", this.picker.style.zIndex = this.options.zIndex;
            const e = t || this.options.element,
                i = e.getBoundingClientRect(),
                o = this.picker.getBoundingClientRect();
            let n = i.bottom,
                r = i.left,
                l = 0,
                c = 0,
                h = 0,
                d = 0;
            if (this.options.parentEl) {
                const t = this.picker.parentNode.getBoundingClientRect();
                n -= t.bottom, (n += i.height) + o.height > window.innerHeight && i.top - t.top - i.height > 0 && (h = i.top - t.top - i.height), (r -= t.left) + o.width > window.innerWidth && i.right - t.right - o.width > 0 && (d = i.right - t.right - o.width)
            } else l = window.scrollX, c = window.scrollY, n + o.height > window.innerHeight && i.top - o.height > 0 && (h = i.top - o.height), r + o.width > window.innerWidth && i.right - o.width > 0 && (d = i.right - o.width);
            this.picker.style.top = `${(h||n)+c}px`, this.picker.style.left = `${(d||r)+l}px`, this.picker.style.right = null, this.picker.style.bottom = null, "function" == typeof this.options.onShow && this.options.onShow.call(this), this.triggerElement = e
        }, n.Litepicker.prototype.hide = function() {
            this.isShowning() && (this.datePicked.length = 0, this.updateInput(), this.options.inlineMode ? this.render() : (this.picker.style.display = "none", "function" == typeof this.options.onHide && this.options.onHide.call(this), this.options.mobileFriendly && (document.body.classList.remove(s.litepickerOpen), this.backdrop.style.display = "none")))
        }, n.Litepicker.prototype.getDate = function() {
            return this.getStartDate()
        }, n.Litepicker.prototype.getStartDate = function() {
            return this.options.startDate ? this.options.startDate.clone() : null
        }, n.Litepicker.prototype.getEndDate = function() {
            return this.options.endDate ? this.options.endDate.clone() : null
        }, n.Litepicker.prototype.setDate = function(t) {
            this.setStartDate(t), "function" == typeof this.options.onSelect && this.options.onSelect.call(this, this.getDate())
        }, n.Litepicker.prototype.setStartDate = function(t) {
            t && (this.options.startDate = new o.DateTime(t, this.options.format, this.options.lang), this.updateInput())
        }, n.Litepicker.prototype.setEndDate = function(t) {
            t && (this.options.endDate = new o.DateTime(t, this.options.format, this.options.lang), this.options.startDate.getTime() > this.options.endDate.getTime() && (this.options.endDate = this.options.startDate.clone(), this.options.startDate = new o.DateTime(t, this.options.format, this.options.lang)), this.updateInput())
        }, n.Litepicker.prototype.setDateRange = function(t, e) {
            this.setStartDate(t), this.setEndDate(e), this.updateInput(), "function" == typeof this.options.onSelect && this.options.onSelect.call(this, this.getStartDate(), this.getEndDate())
        }, n.Litepicker.prototype.gotoDate = function(t, e = 0) {
            this.calendars[e] = new o.DateTime(t), this.render()
        }, n.Litepicker.prototype.setLockDays = function(t) {
            this.options.lockDays = o.DateTime.convertArray(t, this.options.lockDaysFormat), this.render()
        }, n.Litepicker.prototype.setBookedDays = function(t) {
            this.options.bookedDays = o.DateTime.convertArray(t, this.options.bookedDaysFormat), this.render()
        }, n.Litepicker.prototype.setOptions = function(t) {
            delete t.element, delete t.elementEnd, delete t.parentEl, t.startDate && (t.startDate = new o.DateTime(t.startDate, this.options.format, this.options.lang)), t.endDate && (t.endDate = new o.DateTime(t.endDate, this.options.format, this.options.lang)), this.options = Object.assign(Object.assign({}, this.options), t), !this.options.singleMode || this.options.startDate instanceof Date || (this.options.startDate = null, this.options.endDate = null), this.options.singleMode || this.options.startDate instanceof Date && this.options.endDate instanceof Date || (this.options.startDate = null, this.options.endDate = null);
            for (let t = 0; t < this.options.numberOfMonths; t += 1) {
                const e = this.options.startDate ? this.options.startDate.clone() : new o.DateTime;
                e.setMonth(e.getMonth() + t), this.calendars[t] = e
            }
            this.render(), this.options.inlineMode && this.show(), this.updateInput()
        }, n.Litepicker.prototype.destroy = function() {
            this.picker && this.picker.parentNode && (this.picker.parentNode.removeChild(this.picker), this.picker = null), this.backdrop && this.backdrop.parentNode && this.backdrop.parentNode.removeChild(this.backdrop)
        }
    }, function(t, e, i) {
        "use strict";

        function o() {
            return window.matchMedia("(orientation: portrait)").matches ? "portrait" : "landscape"
        }
        Object.defineProperty(e, "__esModule", {
            value: !0
        }), e.isMobile = function() {
            const t = "portrait" === o();
            return window.matchMedia(`(max-device-${t?"width":"height"}: 480px)`).matches
        }, e.getOrientation = o
    }]).Litepicker
}));