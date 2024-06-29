import { useState } from "react";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import "react-datepicker/dist/react-datepicker-cssmodules.css";

type CalendarProps = {
  handleDate: (date: Date | null) => void;
};

export const Calendar = (props: CalendarProps) => {
  const [startDate, setStartDate] = useState<Date | null>(new Date());
  props.handleDate(startDate)
  return (
    <DatePicker
      selected={startDate}
      onChange={(date) => {
        props.handleDate(date);
        setStartDate(date);
      }}
    />
  );
};
